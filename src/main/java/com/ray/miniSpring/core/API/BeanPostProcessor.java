package com.ray.miniSpring.core.API;

import com.ray.miniSpring.core.beans.exception.BeansException;

/**
 * This interface give a chance to modify the bean at it's last period in creation.
 * The calling flow is:
 * 1. {@link BeanPostProcessor#postProcessBeforeInitialization}
 * 2. Init methods called on bean
 * 3. {@link BeanPostProcessor#postProcessAfterInitialization}
 */
public interface BeanPostProcessor {

    /**
     * Called when the bean is just about to call the init-method and do all initiation work.
     * If {@code null} is returned, all post processor will not continue to call
     * {@link BeanPostProcessor#postProcessBeforeInitialization}
     *
     * @param bean     the original bean
     * @param beanName name of the bean
     * @return the modified bean
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Called when the bean has just called the init-method and do all initiation work.
     * If {@code null} is returned, all post processor will not continue to call
     * {@link BeanPostProcessor#postProcessAfterInitialization}
     *
     * @param bean     the original bean
     * @param beanName the name of the bean
     * @return the modified bean
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
