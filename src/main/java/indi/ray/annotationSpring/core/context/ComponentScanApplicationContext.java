package indi.ray.annotationSpring.core.context;

import indi.ray.annotationSpring.core.annotations.ComponentScan;
import indi.ray.annotationSpring.core.beans.definition.ScopeEnum;
import indi.ray.annotationSpring.core.beans.exception.BeanCurrentlyInCreationException;
import indi.ray.annotationSpring.core.beans.exception.BeanNotFoundException;
import indi.ray.annotationSpring.core.beans.exception.BeanNotOfRequiredTypeException;
import indi.ray.annotationSpring.core.beans.exception.BeansException;
import indi.ray.annotationSpring.core.beans.exception.DumplicatedBeanException;
import indi.ray.annotationSpring.core.beans.factory.BeanFactory;
import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.support.BeanDependencyRegistry;
import indi.ray.annotationSpring.core.beans.support.DefaultSingletonBeanRegistry;
import indi.ray.annotationSpring.core.context.creator.BeanCreator;
import indi.ray.annotationSpring.core.context.creator.DefaultBeanCreator;
import indi.ray.annotationSpring.core.context.populator.BeanFiller;
import indi.ray.annotationSpring.core.context.populator.DefaultBeanFiller;
import indi.ray.annotationSpring.core.context.scanner.ClassPathBeanDefinitionsProvider;
import indi.ray.annotationSpring.core.utils.ArrayUtils;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComponentScanApplicationContext implements BeanFactory {
    private static final Logger logger = Logger.getLogger(ComponentScanApplicationContext.class);

    /** BeanDefinitions class -> beanName array */
    private Map<Class<?>, Set<String>> beanNamesByClassMap = new HashMap<Class<?>, Set<String>>(256);


    /** BeanDefinitions beanName -> definition */
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>(256);

    /** cache for singletons */
    private Map<String, Object> cachedSingletons = new HashMap<String, Object>(256);

//    /** cache for prototypes */
////    private Map<String, Object> cachedPrototypes = new HashMap<String, Object>(256);

    private Set<String> singletonsInCreation = new HashSet<String>(16);

    private Set<String> protoTypessInCreation = new HashSet<String>(16);

    private ClassPathBeanDefinitionsProvider beanDefinitionsProvider = new ClassPathBeanDefinitionsProvider();

    private BeanDependencyRegistry dependencyRegistry = new DefaultSingletonBeanRegistry();

    private BeanCreator beanCreator = new DefaultBeanCreator();

    private BeanFiller beanFiller = new DefaultBeanFiller();

    private Class<?> componentScanClass;


    public ComponentScanApplicationContext(Class<?> componentScanClass) {
        this.componentScanClass = componentScanClass;
        AssertUtils.assertTrue(componentScanClass.isAnnotationPresent(ComponentScan.class), "需要标注@ComponentScan");
        refresh();
    }

    /**
     * refresh the context, do all prepare work
     */
    public void refresh() {
        prepareRefresh();
        scanBeanDefinitions(); // load bean defs here
        postProcessBeanFactory(); //
        invokeBeanFactoryPostProcessors(); // invoke factory processors registered as beans in the context
        registerBeanPostProcessors(); // Register bean processors that intercept bean creation
        finishBeanFactoryInitialization(); // Instantiate all remaining (non lazy-init) singletons
    }

    private void prepareRefresh() {
        //todo
    }

    private void scanBeanDefinitions() {
        // scan all bean definitions
        ComponentScan componentScan = this.componentScanClass.getAnnotation(ComponentScan.class);
        int packagesLength = ArrayUtils.getLength(componentScan.basePackages());
        int classesLength = ArrayUtils.getLength(componentScan.basePackageClasses());
        String[] basePaths = new String[packagesLength + classesLength];
        System.arraycopy(componentScan.basePackages(), 0, basePaths, 0, packagesLength);
        for (int i = 0; i < classesLength; i++) {
            basePaths[i + packagesLength] = componentScan.basePackageClasses()[i].getPackage().getName().
                    replaceAll("\\.", File.separator);
        }

        // load and archive bean definitions
        Set<BeanDefinition> beanDefinitionSet = this.beanDefinitionsProvider.findCandidateComponents(basePaths);
        for (BeanDefinition beanDefinition : beanDefinitionSet) {
            String beanName = beanDefinition.getBeanName();
            if (this.beanDefinitionMap.containsKey(beanName)) {
                throw new DumplicatedBeanException("重复的Bean" + beanName);
            }
            this.beanDefinitionMap.put(beanName, beanDefinition);
        }

        for (Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            Class<?> clazz = entry.getValue().getBeanClass();
            Set<String> names = this.beanNamesByClassMap.get(clazz);
            if (names == null) {
                names = new HashSet<String>(1);
                this.beanNamesByClassMap.put(clazz, names);
            }
            names.add(entry.getKey());
        }

        // todo, resolve depend on relations
    }

    private void postProcessBeanFactory() {
        // todo
    }

    private void invokeBeanFactoryPostProcessors() {
        // todo
    }

    private void registerBeanPostProcessors() {
        // todo
    }

    private void finishBeanFactoryInitialization() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScopeEnum() == ScopeEnum.SINGLETON && !beanDefinition.isLazyInit()) {
                getBean(entry.getKey());
            }
        }
    }

    private <T> T doGetBean(String beanName, final Class<T> requiredType) {
        // check for singleton cache
        Object bean = this.cachedSingletons.get(beanName);
        if (bean != null) {
            logger.debug("从缓存中获取单例" + beanName);
            return checkTypeForBean(beanName, bean, requiredType);
        }
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (null == beanDefinition) {
            throw new BeanNotFoundException("Bean not found for name " + beanName);
        }

        // have to create new Bean
        if (beanDefinition.getScopeEnum() == ScopeEnum.SINGLETON) {
            beforeSingletonCreation(beanName);
            bean = this.beanCreator.createBean(beanDefinition, this);
            afterSingletonCreation(beanName, bean);
        } else {
            beforePrototypeCreation(beanName);
            bean = this.beanCreator.createBean(beanDefinition, this);
            afterPrototypeCreation(beanName, bean);
        }

        // populate bean
        this.beanFiller.populateBean(bean, beanDefinition, this);

        return checkTypeForBean(beanName, bean, requiredType);
    }

    private void beforeSingletonCreation(String beanName) {
        if (!this.singletonsInCreation.add(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }

    private void afterSingletonCreation(String beanName, Object bean) {
        if (bean == null) {
            throw new BeanNotFoundException(beanName + " not found");
        }
        if (!this.singletonsInCreation.remove(beanName)) {
            throw new IllegalStateException(beanName + " isn't currently in creation");
        }
        this.cachedSingletons.put(beanName, bean);
    }

    private void afterPrototypeCreation(String beanName, Object bean) {
        if (bean == null) {
            throw new BeanNotFoundException(beanName + " not found");
        }
        if (!this.protoTypessInCreation.remove(beanName)) {
            throw new IllegalStateException(beanName + " isn't currently in creation");
        }
    }

    private void beforePrototypeCreation(String beanName) {
        if (!this.protoTypessInCreation.add(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }

    private <T> T checkTypeForBean(String beanName, Object bean, final Class<T> requiredType) {
        if (requiredType != null && !requiredType.isAssignableFrom(bean.getClass())) {
            throw new BeanNotOfRequiredTypeException(beanName, requiredType, bean.getClass());
        }
        return (T) bean;
    }

    /**
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     * @see BeanFactory#getBean(String)
     */
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    /**
     * @param beanName      the name of the bean
     * @param requiredClass type the bean must match. Can be an interface or superclass of the
     *                      actual class, or {@Code null} for any match
     * @param <T>
     * @return
     * @throws BeansException
     * @see BeanFactory#getBean(String, Class)
     */
    public <T> T getBean(String beanName, Class<T> requiredClass) throws BeansException {
        return doGetBean(beanName, requiredClass);
    }

    //  todo, 支持超类get
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        AssertUtils.assertNotNull(requiredType, "指定类型不能为null");
        // find bean name of the class
        Set<String> beanNames = this.beanNamesByClassMap.get(requiredType);
        if (beanNames == null || beanNames.size() == 0) {
            throw new BeanNotFoundException("未找到类型为 " + requiredType + "的bean");
        }
        String beanName = null;
        if (beanNames.size() == 1) {
            beanName = beanNames.toArray(new String[1])[0];
        } else {
            boolean found = false;
            for (String name : beanNames) {
                BeanDefinition bd = this.beanDefinitionMap.get(name);
                if (bd.isPrimary()) {
                    if (found) {
                        throw new BeansException("类型" + requiredType + "有多个标注为@Primary的bean:" + name + "," + beanName);
                    } else {
                        found = true;
                        beanName = name;
                    }
                }
            }
            if (!found) {
                throw new BeansException("类型" + requiredType + "有多个bean, 无法决定使用哪一个，请使用@Primary标注");
            }
        }

        return doGetBean(beanName, requiredType);
    }

    /**
     * @param name the name of the bean to be query
     * @return
     * @see BeanFactory#containsBean(String)
     */
    public boolean containsBean(String name) {
        return this.cachedSingletons.containsKey(name) || this.beanDefinitionMap.containsKey(name);
    }


}
