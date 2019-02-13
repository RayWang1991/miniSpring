package com.ray.miniSpring.core.beans.exception;

public class BeanNotOfRequiredTypeException extends BeansException {
    public BeanNotOfRequiredTypeException(String beanName, Class<?> requiredType, Class<?> beanType) {
        super("Bean names " + beanName + " is expected to be of type: " + requiredType + ", but was actually of type "
                + beanType);
    }
}
