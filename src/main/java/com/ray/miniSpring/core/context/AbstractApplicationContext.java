package com.ray.miniSpring.core.context;

import com.ray.miniSpring.core.API.BeanFactoryAware;
import com.ray.miniSpring.core.API.FactoryBean;
import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.beans.exception.BeanCurrentlyInCreationException;
import com.ray.miniSpring.core.context.creator.DefaultBeanCreator;
import com.ray.miniSpring.core.context.populator.BeanPopulater;
import com.ray.miniSpring.core.context.populator.DefaultBeanPopulater;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.API.BeanPostProcessor;
import com.ray.miniSpring.core.beans.definition.ScopeEnum;
import com.ray.miniSpring.core.beans.exception.BeanNotFoundException;
import com.ray.miniSpring.core.beans.exception.BeanNotOfRequiredTypeException;
import com.ray.miniSpring.core.beans.exception.BeansException;
import com.ray.miniSpring.core.beans.exception.DumplicatedBeanException;
import com.ray.miniSpring.core.beans.factory.BeanFactory;
import com.ray.miniSpring.core.context.creator.BeanCreator;
import com.ray.miniSpring.core.utils.OrderUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractApplicationContext implements BeanFactory {
    private static final Logger logger = Logger.getLogger(AbstractApplicationContext.class);

    protected BeanCreator beanCreator = new DefaultBeanCreator();

    protected BeanPopulater beanPopulater = new DefaultBeanPopulater();

    /** BeanDefinitions class -> beanName array */
    protected Map<Class<?>, Set<String>> beanNamesByClassMap = new HashMap<Class<?>, Set<String>>(256);


    /** BeanDefinitions beanName -> definition */
    protected Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>(256);

    /** cache for complete singletons */
    protected Map<String, Object> cachedSingletons = new HashMap<String, Object>(256);

    /** cache for early exposed singletons */
    protected Map<String, Object> earlySingletons = new HashMap<String, Object>(256);

    /** singletons in creation */
    protected Set<String> singletonsInCreation = new HashSet<String>(16);

    /** prototypes in creation */
    protected Set<String> protoTypesInCreation = new HashSet<String>(16);

    protected List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

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

    protected void clear() {
        this.beanNamesByClassMap.clear();
        this.beanDefinitionMap.clear();
        this.cachedSingletons.clear();
        this.beanPostProcessors.clear();
        this.protoTypesInCreation.clear();
        this.singletonsInCreation.clear();
    }

    /**
     * Subclass should do prepare work here, e.g. clear the containers
     */
    protected abstract void prepareRefresh();

    protected abstract void scanBeanDefinitions();

    protected abstract void postProcessBeanFactory();

    protected abstract void invokeBeanFactoryPostProcessors();

    protected void registerBeanPostProcessors() {
        // todo: find another way to sort processors
        // 1.find all beanNames for BeanPostProcessors
        Set<String> postProcessorNames = new LinkedHashSet<String>(8);
        for (Map.Entry<Class<?>, Set<String>> entry : this.beanNamesByClassMap.entrySet()) {
            if (BeanPostProcessor.class.isAssignableFrom(entry.getKey())) {
                postProcessorNames.addAll(entry.getValue());
            }
        }

        // 2.sort them
        List<BeanPostProcessor> postProcessors = new ArrayList<BeanPostProcessor>();
        for (String name : postProcessorNames) {
            BeanPostProcessor postProcessor = this.getBean(name, BeanPostProcessor.class);
            postProcessors.add(postProcessor);
        }

        Collections.sort(postProcessors, OrderUtils.DEFALT_COMPARATOR);

        // 3.register them
        for (BeanPostProcessor postProcessor : postProcessors) {
            this.addPostProcessor(postProcessor);
        }
    }

    protected void addPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    protected abstract void finishBeanFactoryInitialization();

    private <T> T doGetBean(String beanName, final Class<T> requiredType) {
        // check for singleton cache
        Object bean = this.cachedSingletons.get(beanName);
        if (bean == null) {
            bean = this.earlySingletons.get(beanName);
        }

        if (bean != null) {
            logger.debug("从缓存中获取单例" + beanName);
            return getBeanInstanceFromBean(beanName, bean, requiredType);
        }
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (null == beanDefinition) {
            throw new BeanNotFoundException("Bean not found for name " + beanName);
        }

        // have to create new Bean
        bean = doCreateBean(beanName, beanDefinition);

        return getBeanInstanceFromBean(beanName, bean, requiredType);
    }

    private Object doCreateBean(String beanName, BeanDefinition bd) {
        Object bean;
        boolean isSingleton = bd.getScopeEnum() == ScopeEnum.SINGLETON;

        if (isSingleton) {
            beforeSingletonCreation(beanName);
            bean = this.beanCreator.createBean(bd, this);
            afterSingletonCreation(beanName, bean);
        } else {
            beforePrototypeCreation(beanName);
            bean = this.beanCreator.createBean(bd, this);
            afterPrototypeCreation(beanName, bean);
        }

        // populate bean
        populateBean(bean, bd);

        // initiateBean
        Object val = initiateBean(bean, beanName);

        if (isSingleton) {
            this.cachedSingletons.put(beanName, val);
            this.earlySingletons.remove(beanName);
        }

        return val;
    }

    private void populateBean(Object bean, BeanDefinition bd) {
        this.beanPopulater.populateBean(bean, bd, this);
    }

    private Object initiateBean(Object bean, String beanName) {
        invokeAwareMethods(bean, beanName);

        Object val = bean;
        val = postProcessOnBeanBeforeInitiation(bean, beanName);
        invokeInitMethod(val);
        postProcessOnBeanAfterInitiation(val, beanName);

        return val;
    }

    private void invokeAwareMethods(Object bean, String beanName) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

    private void invokeInitMethod(Object bean) {
        //todo, support init-method
    }

    private Object postProcessOnBeanBeforeInitiation(Object bean, String beanName) {
        Object val = bean;
        for (BeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
            val = beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            if (val == null) {
                break;
            }
        }
        return val;
    }

    private Object postProcessOnBeanAfterInitiation(Object bean, String beanName) {
        Object val = bean;
        for (BeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
            val = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
            if (val == null) {
                break;
            }
        }
        return val;
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
        this.earlySingletons.put(beanName, bean);
    }

    private void afterPrototypeCreation(String beanName, Object bean) {
        if (bean == null) {
            throw new BeanNotFoundException(beanName + " not found");
        }
        if (!this.protoTypesInCreation.remove(beanName)) {
            throw new IllegalStateException(beanName + " isn't currently in creation");
        }
    }

    private void beforePrototypeCreation(String beanName) {
        if (!this.protoTypesInCreation.add(beanName)) {
            throw new BeanCurrentlyInCreationException(beanName);
        }
    }

    private <T> T getBeanInstanceFromBean(String beanName, Object bean, Class<T> requiredType) {
        Object instance = bean;
        if (bean instanceof FactoryBean) {
            instance = ((FactoryBean) bean).getObject();
        }
        return checkTypeForBean(beanName, instance, requiredType);
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
                        throw new BeansException("类型" + requiredType + "有多个设置为Primary的bean:" + name + "," + beanName);
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


    /**
     * @param beanName the name of bean
     * @return
     * @see BeanFactory#isSingleton(String)
     */
    public boolean isSingleton(String beanName) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanName);
        return bd == null || bd.getScopeEnum() == ScopeEnum.SINGLETON;
    }

    /**
     * @param name the name of the bean
     * @return
     * @see BeanFactory#getBeanType
     */
    public Class<?> getBeanType(String name) {
        if (this.cachedSingletons.containsKey(name)) {
            return this.cachedSingletons.get(name).getClass();
        } else if (this.beanDefinitionMap.containsKey(name)) {
            BeanDefinition bd = this.beanDefinitionMap.get(name);
            return bd.getBeanClass();
        }
        return null;
    }

    protected void loadBeanDefinitions(Collection<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
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
    }

    protected void loadAllSingletons() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScopeEnum() == ScopeEnum.SINGLETON && !beanDefinition.isLazyInit()) {
                getBean(entry.getKey());
            }
        }
    }
}
