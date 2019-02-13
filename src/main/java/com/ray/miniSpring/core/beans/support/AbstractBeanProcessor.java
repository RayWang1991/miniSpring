package com.ray.miniSpring.core.beans.support;

import com.ray.miniSpring.core.beans.exception.BeansException;
import com.ray.miniSpring.core.API.BeanPostProcessor;

/**
 * Convenient base class for class to implement {@link BeanPostProcessor} interface
 */
public abstract class AbstractBeanProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
