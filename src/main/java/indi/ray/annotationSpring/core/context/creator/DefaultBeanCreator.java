package indi.ray.annotationSpring.core.context.creator;

import com.sun.tools.internal.jxc.ap.Const;
import indi.ray.annotationSpring.core.beans.definition.BeanConstructor;
import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.definition.DependOnRelation;
import indi.ray.annotationSpring.core.beans.exception.BeanNotFoundException;
import indi.ray.annotationSpring.core.beans.exception.BeansException;
import indi.ray.annotationSpring.core.beans.factory.BeanFactory;
import indi.ray.annotationSpring.core.utils.ArrayUtils;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;

public class DefaultBeanCreator implements BeanCreator {
    private static final Logger logger = Logger.getLogger(DefaultBeanCreator.class);

    /**
     * @param beanDefinition
     * @param beanFactory
     * @return
     * @see BeanCreator#createBean(BeanDefinition, BeanFactory)
     */
    public Object createBean(BeanDefinition beanDefinition, BeanFactory beanFactory) {
        BeanConstructor beanConstructor = beanDefinition.getConstructor();
        Constructor<?> constructor = beanConstructor.getConstructor();
        DependOnRelation[] dependOnRelations = beanConstructor.getDependOnRelations();
        if (ArrayUtils.getLength(dependOnRelations) == 0) {
            return doCreateBean(constructor);
        }

        Object[] args = new Object[dependOnRelations.length];
        for (int i = 0; i < dependOnRelations.length; i++) {
            DependOnRelation relation = dependOnRelations[i];
            String requiredName = relation.getRequiredName();
            Class<?> requiredType = relation.getRequiredType();
            Object arg = null;
            if (StringUtils.isNotBlank(requiredName)) {
                try {
                    arg = beanFactory.getBean(requiredName);
                } catch (BeanNotFoundException e) {
                    logger.error("Bean not found for name: " + requiredName + " continue to find with class");
                }
            }
            if (arg == null) {
                try {
                    arg = beanFactory.getBean(requiredType);
                } catch (BeanNotFoundException e) {
                    logger.error("Bean not found for class: " + requiredType);
                }
            }
            if (arg == null && relation.isRequired()) {
                throw new BeanNotFoundException("can not found bean, name:" + requiredName + " class: " + requiredType);
            }
            args[i] = arg;
        }

        return doCreateBean(constructor, args);
    }

    private Object doCreateBean(Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            // todo, 这里考虑把异常情况统一放在beanFactory处理?
            e.printStackTrace();
            return null;
        }
    }
}
