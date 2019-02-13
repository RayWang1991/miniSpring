package com.ray.miniSpring.core.API;

import com.ray.miniSpring.core.beans.factory.BeanFactory;

/**
 * Bean implements this interface will get the {@link BeanFactory} instance that
 * create the bean as soon as it's created
 */
public interface BeanFactoryAware {

    /**
     * set the beanFactory instance to the bean
     *
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory);
}
