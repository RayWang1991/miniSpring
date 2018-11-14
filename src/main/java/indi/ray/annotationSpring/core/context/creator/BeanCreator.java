package indi.ray.annotationSpring.core.context.creator;

import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.factory.BeanFactory;

public interface BeanCreator {
    /**
     * Create bean based on bean definition. The bean factory is used for solving dependency
     * relationship between beans. return {@code null}if we can not create such bean
     * @param beanDefinition
     * @param beanFactory
     * @return
     */
    Object createBean(BeanDefinition beanDefinition, BeanFactory beanFactory);
}
