package indi.ray.miniSpring.core.beans.factory;

import indi.ray.miniSpring.core.beans.exception.BeansException;

public abstract class AbstractBeanFactory implements BeanFactory {
    
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

    public Class<?> getBeanType(String name) {
        return null;
    }

    public boolean isSingleton(String beanName) {
        return false;
    }
}
