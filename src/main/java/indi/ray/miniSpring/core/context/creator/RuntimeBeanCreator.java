package indi.ray.miniSpring.core.context.creator;


import indi.ray.miniSpring.core.beans.definition.RuntimeBeanConstructorResolver;

public interface RuntimeBeanCreator extends BeanCreator {
    void setRuntimeBeanConstructorResolver(RuntimeBeanConstructorResolver beanConstructorResolver);
}
