package indi.ray.annotationSpring.core.context.populator;

import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.factory.BeanFactory;

/**
 * This interface describes a populator for bean, that is
 * to fill the properties for bean.
 * The bean should be validated as a given type
 */
public interface BeanFiller {
    public void populateBean(Object bean, BeanDefinition beanDefinition, BeanFactory beanFactory);
}
