package com.ray.miniSpring.core.context.creator;


import com.ray.miniSpring.core.beans.definition.RuntimeBeanConstructorResolver;

public interface RuntimeBeanCreator extends BeanCreator {
    void setRuntimeBeanConstructorResolver(RuntimeBeanConstructorResolver beanConstructorResolver);
}
