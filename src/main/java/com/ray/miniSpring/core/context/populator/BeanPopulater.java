package com.ray.miniSpring.core.context.populator;

import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.beans.factory.BeanFactory;

/**
 * This interface describes a populator for bean, that is
 * to fill the properties for bean.
 * The bean should be validated as a given type
 */
public interface BeanPopulater {
    public void populateBean(Object bean, BeanDefinition beanDefinition, BeanFactory beanFactory);
}
