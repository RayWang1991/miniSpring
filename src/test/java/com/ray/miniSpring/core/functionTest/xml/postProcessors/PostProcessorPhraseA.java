package com.ray.miniSpring.core.functionTest.xml.postProcessors;

import com.ray.miniSpring.core.API.Ordered;
import com.ray.miniSpring.core.beans.exception.BeansException;
import com.ray.miniSpring.core.API.BeanPostProcessor;

public class PostProcessorPhraseA implements BeanPostProcessor, Ordered {
    private Counter counter;

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public int getOrder() {
        return HIGHEST_ORDER;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        counter.count();
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
