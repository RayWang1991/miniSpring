package indi.ray.miniSpring.core.functionTest.xml.postProcessors;

import indi.ray.miniSpring.core.API.BeanPostProcessor;
import indi.ray.miniSpring.core.API.Ordered;
import indi.ray.miniSpring.core.beans.exception.BeansException;

public class PostProcessorPhraseB implements BeanPostProcessor, Ordered {
    private Counter counter;

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public int getOrder() {
        return HIGHEST_ORDER + 1;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        counter.doDouble();
        return bean;
    }
}
