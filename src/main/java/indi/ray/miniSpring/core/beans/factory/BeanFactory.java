package indi.ray.miniSpring.core.beans.factory;

import indi.ray.miniSpring.core.beans.exception.BeansException;
import indi.ray.miniSpring.core.beans.exception.NoSuchBeanDefinitionException;

/**
 * The root interface for accessing a Spring bean container
 *
 * <p>This interface is implemented by objects that hold a number
 * of bean definitions, each uniquely identified by a String name.
 * Depending on the bean definition, the factory will return either
 * an independent instance of a contained object (prototype design pattern),
 * or a single shared instance (a superior alternative to the Singleton design
 * pattern), in which the instance is a singleton in the scope of the factory.
 * </p>
 *
 * <p>The point of this approach is that the BeanFactory is a central registry of
 * application components, and centralizes configuration of application components.
 * </p>
 *
 * <p>Note that is is generally better to rely on Dependency Injection("push" configurations)
 * to configure application objects through setters or constructors, rather than use any for of
 * "pull" configurations like a BeanFactory lookup. Spring's Dependency Injection functionality
 * is implemented using this BeanFactory interfaces and its subinterfaces.</p>
 */

public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "&";

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     *
     * @param beanName the name of the bean
     * @return an instance of the bean
     * @throws NoSuchBeanDefinitionException if there is no bean definition
     * @throws BeansException                if the bean could not be obtained
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * Return an instance for the required type
     *
     * @param requiredType
     * @param <T>          the generic type
     * @return
     * @throws BeansException if the bean can not be obtained
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
     * safety by throwing {@Code BeanNotOfRequiredTypeException} if the bean is not of the
     * required type. This means that ClassCastException can't be thrown on casting the
     * result correctly
     * </p>
     *
     * @param beanName      the name of the bean
     * @param requiredClass type the bean must match. Can be an interface or superclass of the
     *                      actual class, or {@Code null} for any match
     * @param <T>           the given type
     * @return an instance of the bean
     * @throws BeansException
     */
    <T> T getBean(String beanName, Class<T> requiredClass) throws BeansException;

    /**
     * Does this bean factory contains a bean definition or externally registered singleton instance
     * with the given name ?
     *
     * <p>If the factory is hierarchical, will ask any parent factory if the bean cannot be found in
     * this factory instance
     * </p>
     * <p>If a bean definition or singleton instace matching the given name is found,
     * this method will return {@code true} whether the named bean definition is concrete or abstract,
     * lazy or eager, in scope or not. Therefore, note that a returned {@code true} from this method
     * does not necessarily indicate that {@link #getBean} will be able to obtain an instance for the
     * same name.
     * </p>
     *
     * @param name the name of the bean to be query
     * @return whether a bean with the given name is present
     */
    boolean containsBean(String name);

    /**
     * get the class for bean, typically to resolve type match problem
     *
     * @param name the name of the bean
     * @return the class of the bean
     */
    Class<?> getBeanType(String name);
}


