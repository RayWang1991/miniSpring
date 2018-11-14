package indi.ray.annotationSpring.core.beans.exception;

import java.beans.Beans;

public class BeanNotOfRequiredTypeException extends BeansException {
    public BeanNotOfRequiredTypeException(String beanName, Class<?> requiredType, Class<?> beanType) {
        super("Bean names " + beanName + " is expected to be of type: " + requiredType + ", but was actually of type "
                + beanType);
    }
}
