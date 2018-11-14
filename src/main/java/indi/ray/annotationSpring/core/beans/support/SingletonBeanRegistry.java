package indi.ray.annotationSpring.core.beans.support;

/**
 * Interface that defines a registry for shared bean instances.
 * Implementations should be in order to expose their singleton
 * management facility in a uniform manner.
 */

public interface SingletonBeanRegistry {

    /**
     * register an existing singleton object with a given name
     * @param beanName the name of the bean
     * @param singletonObj the existing singleton object
     */
    void registerSingleton(String beanName, Object singletonObj);

    /**
     * Return the (raw) singleton object registered under the given name.
     * @param beanName the name of the bean
     * @return the registered existing singleton object, or ({@code null} if none
     */
    Object getSingleton(String beanName);

    /**
     * Check if the registry contains a singleton instance with the given name.
     * @param beanName the name of the bean
     * @return @{@code true} if the registry contains a singleton instance with the given name, {@code false} if not
     */
    boolean containsSingleton(String beanName);

    /**
     * Return the names of singleton beans registered in this registry.
     * @return the list of names as a String array (never {@code null})
     */
    String[] getSingletonNames();

    /**
     * Return the count of singleton names in this registry
     * @return the count of singleton names in this registry
     */
    int getSingletonCount();
}
