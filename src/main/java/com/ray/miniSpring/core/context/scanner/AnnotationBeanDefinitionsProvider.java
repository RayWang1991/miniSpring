package com.ray.miniSpring.core.context.scanner;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.DependsOn;
import com.ray.miniSpring.core.annotations.Primary;
import com.ray.miniSpring.core.annotations.Qualifier;
import com.ray.miniSpring.core.annotations.Scope;
import com.ray.miniSpring.core.beans.definition.AnnotationBeanDefinition;
import com.ray.miniSpring.core.beans.definition.BeanConstructor;
import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.beans.definition.BeanProperty;
import com.ray.miniSpring.core.beans.definition.DefaultValueOrRef;
import com.ray.miniSpring.core.beans.definition.ScopeEnum;
import com.ray.miniSpring.core.beans.definition.ValueOrRef;
import com.ray.miniSpring.core.beans.exception.BeansException;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.utils.BeanDefinitionUtils;
import com.ray.miniSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * AnnotationBeanDefinitionsProvider provide 2 functions
 * 1. Scan the path of the packages, get the class names
 * 2. Generate BeanDefinitions for the class
 */
public class AnnotationBeanDefinitionsProvider {
    private static final Logger logger = Logger.getLogger(AnnotationBeanDefinitionsProvider.class);

    private AnnotationResourceLoader resourceLoader = new AnnotationResourceLoader();

    /**
     * given one or more basePackage path, find all candidate beans
     *
     * @param basePackages the package paths to be Scanned
     * @return the set of bean definitions of the candidates
     */
    public List<BeanDefinition> findCandidateComponents(String... basePackages) {
        // compress packages
        String[] compressedPackages = resourceLoader.compressPackages(basePackages);

        // find classNames
        Set<String> classNames = resourceLoader.findClassNamesForGivenPaths(compressedPackages);

        // generate bean definitions
        List<BeanDefinition> beanDefinitions = generateBeanDefinitions(classNames);

        return beanDefinitions;
    }

    /**
     * given a collection of class names, find the candidates among them and
     * generate bean definitions for them
     *
     * @param classNames the class names
     * @return {@code Set} of {@code BeanDefinitions}
     */
    public List<BeanDefinition> generateBeanDefinitions(Collection<String> classNames) {
        List<BeanDefinition> bds = new ArrayList<BeanDefinition>(classNames.size());
        for (String className : classNames) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                // should not be
                logger.error(e);
                continue;
            }
            BeanDefinition bd = generateBeanDefinition(clazz);
            if (bd != null) {
                bds.add(bd);
            }
        }
        return bds;
    }

    /**
     * given a certain {@code class}, generate the {@code BeanDefinition} of the class
     *
     * @param clazz
     * @return
     */
    public BeanDefinition generateBeanDefinition(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Component.class)) {
            return null;
        }

        if (clazz.isAnnotation() || clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }

        AnnotationBeanDefinition bd = new AnnotationBeanDefinition();

        bd.setBeanClass(clazz);

        Component component = clazz.getAnnotation(Component.class);
        if (StringUtils.isNotBlank(component.value())) {
            bd.setBeanName(component.value());
        } else {
            bd.setBeanName(StringUtils.unCapitalize(clazz.getSimpleName()));
        }
        bd.setLazyInit(component.lazyInit());

        Scope scope = clazz.getAnnotation(Scope.class);
        if (scope != null && scope.value() == ScopeEnum.PROTOTYPE) {
            bd.setScopeEnum(ScopeEnum.PROTOTYPE);
        } else {
            bd.setScopeEnum(ScopeEnum.SINGLETON);
        }

        if (clazz.getAnnotation(Primary.class) != null) {
            bd.setPrimary(true);
        } else {
            bd.setPrimary(false);
        }

        // depends on
        DependsOn dependsOn = clazz.getAnnotation(DependsOn.class);
        if (dependsOn != null) {
            bd.setDependsOnBeanNames(dependsOn.value());
        }

        // analyze field dependencies
        List<BeanProperty> fieldList = bd.getBeanProperties();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                String name = field.getName();
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (qualifier != null && StringUtils.isNotBlank(qualifier.value())) {
                    name = qualifier.value();
                }
                BeanProperty bp = new BeanProperty();
                ValueOrRef valueOrRef = DefaultValueOrRef.ref(field.getType(),name,autowired.required());
                bp.setFieldValue(valueOrRef);
                bp.setFieldName(name);
                bp.setField(field);
                fieldList.add(bp);
            }
        }
        // analyze constructor dependencies
        Constructor[] cts = clazz.getDeclaredConstructors();
        Constructor constructor = null;
        boolean found = false;
        for (Constructor ct : cts) {
            if (ct.isAnnotationPresent(Autowired.class)) {
                if (found) {
                    throw new BeansException("只允许有一个构造器标注为@Autowired");
                } else {
                    found = true;
                    constructor = ct;
                }
            }
        }

        if (constructor != null) {
            bd.setConstructor(buildBeanConstructor(constructor, false));
        } else {
            try {
                Constructor<?> defaultCt = clazz.getDeclaredConstructor();
                bd.setConstructor(buildBeanConstructor(defaultCt, true));
            } catch (Exception e) {
                logger.error(e);
                throw new BeansException("类必须有标注为Autowired的构造器或者无参数构造器");
            }
        }

        return BeanDefinitionUtils.validateAndReturn(bd, logger);
    }

    /**
     * Build bean constructor wrapper with certain constructor for bean.
     * There are 2 cases: a constructor with @Autowried annotation or a default no-arg
     * constructor
     *
     * @param constructor          the constructor for the bean
     * @param isDefaultConstructor the constructor wrapper
     * @return
     */
    public BeanConstructor buildBeanConstructor(Constructor<?> constructor, boolean isDefaultConstructor) {
        AssertUtils.assertTrue(Modifier.isPublic(constructor.getModifiers()), constructor + "构造器必须为public");
        BeanConstructor bc = new BeanConstructor();
        bc.setConstructor(constructor);
        if (isDefaultConstructor) {
            return bc;
        }
        Class<?>[] parameterizedTypes = constructor.getParameterTypes();
        Annotation[][] annotations = constructor.getParameterAnnotations();
        List<ValueOrRef> refs = new ArrayList<ValueOrRef>(parameterizedTypes.length);
        for (int i = 0; i < parameterizedTypes.length; i++) {
            Class<?> clazz = parameterizedTypes[i];
            String name = StringUtils.unCapitalize(clazz.getSimpleName());
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof Qualifier) {
                    Qualifier qualifier = (Qualifier) annotation;
                    if (StringUtils.isNotBlank(qualifier.value())) {
                        name = qualifier.value();
                    }
                }
            }
            refs.add(DefaultValueOrRef.ref(clazz, name, true));
        }
        bc.setConstructorArgs(refs);
        return bc;
    }


}
