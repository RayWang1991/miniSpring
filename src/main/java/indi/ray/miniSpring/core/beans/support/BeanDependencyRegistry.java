package indi.ray.miniSpring.core.beans.support;

/**
 * This interface indicates that the implementations should manage the dependency relationship
 * of the registered beans
 */
public interface BeanDependencyRegistry {

    /**
     * register the dependency for given bean, dependentBean depends on bean
     * @param beanName the name of the bean
     * @param dependentBeanName the name of the dependent bean
     */
    void registerDependency(String beanName, String dependentBeanName);

    /**
     * return the names of all beans that depend on the specified bean
     * @param beanName the name of the bean
     * @return the dependent beans' names as {@code String[]}, never return {@code null}
     */
    String[] getDependentBeans(String beanName);

    /**
     * return the names of all beans that the specified bean relies on
     * @param beanName the name of the specified bean
     * @return the names of beans the specified bean depends on as {@code String[]},
     * never return {@code null}
     */
    String[] getDependenciesForBean(String beanName);
}
