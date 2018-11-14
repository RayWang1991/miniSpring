package indi.ray.annotationSpring.core.beans.factory;

import indi.ray.annotationSpring.core.beans.exception.BeansException;

public class DefaultBeanFactory implements BeanFactory {
    
    public Object getBean(String beanName) throws BeansException {
        return null;
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    public <T> T getBean(String beanName, Class<T> requiredClass) throws BeansException {
        return null;
    }

    public boolean containsBean(String name) {
        return false;
    }
}
