package indi.ray.annotationSpring.core.beans.definition;

import java.lang.reflect.Constructor;

/**
 * This interface represents the bean definition, to describe a bean instance,
 * which has properties, constructor argument, and more information for concrete
 * implementations.
 */
public interface BeanDefinition {
    /**
     * Return the name of the bean instance
     */
    String getBeanName();

    /**
     * Return the {@code Class} name of the bean
     */
    String getBeanClassName();

    /**
     * Return the {@code Class} for the bean
     */
    Class<?> getBeanClass();

    /**
     * Return the name of the parent bean definition
     */
    String getParentBeanName();

    /**
     * return the scope of the bean
     */
    ScopeEnum getScopeEnum();

    /**
     * return the factory name of the bean, if any
     */
    String getFactoryBeanName();

    /**
     * return the factory method name of the bean, if any
     */
    String getFactoryMethodName();

    /**
     * return the init method name of the bean, if any
     *
     * @return
     */
    String getInitMethodName();

    /**
     * return the names of beans that the bean depends on
     */
    String[] getDependsOnBeanNames();

    /**
     * validate the bean definition
     * @throws IllegalArgumentException if validation fails
     */
    void validate() throws IllegalArgumentException;

    /**
     * primary act as a tie-breaker to solve the multiple-choice problem,
     * typically in a situation when many beans satisfy the same type requirement.
     * @param primary
     */
    void setPrimary(boolean primary);

    /**
     * return whether the bean is primary
     */
    boolean isPrimary();

    /**
     * set the constructor for the bean
     */
    void setConstructor(BeanConstructor constructor);

    /**
     * return the constructor for the bean
     */
    BeanConstructor getConstructor();

    /**
     * set the fields {@literal to be injected} for the bean
     */
    void setFields(BeanField[] fields);

    /**
     * return the fields {@literal to be injected} for the bean
     */
    BeanField[] getFields();

    /**
     * set the lazy init property for the bean,
     * if is {@code true}, then the bean would not be initiated right
     * after the bean factory init
     * @param lazyInit
     */
    void setLazyInit(boolean lazyInit);

    /**
     * Return the lazy init property for the bean
     * if is {@code true}, then the bean would not be initiated right
     * after the bean factory init
     */
    boolean isLazyInit();
}
