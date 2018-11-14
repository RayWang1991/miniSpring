package indi.ray.annotationSpring.core.beans.support;


import indi.ray.annotationSpring.core.beans.exception.ReregisterSingletonException;
import indi.ray.annotationSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry, BeanDependencyRegistry {

    /** logger that can be used for subclasses */
    private final Logger logger = Logger.getLogger(getClass());

    /** singletons: bean name -> bean instance */
    private final Map<String, Object> singletons = new HashMap<String, Object>(256);

    /** names of beans that are currently in creation */
    private final Set<String> singletonCurrentlyInCreation = new HashSet<String>(16);

    /** names of beans that have been registered */
    private final Set<String> registeredSingletons = new HashSet<String>(256);

    /** Map between dependent bean names: bean name -> Set of bean names depending on this bean */
    private final Map<String, Set<String>> dependentBeanMap = new HashMap<String, Set<String>>(256);

    /** Map between depending bean names: bean name -> Set of bean names that this bean depends on */
    private final Map<String, Set<String>> dependenciesForBeanMap = new HashMap<String, Set<String>>(256);

    public void registerSingleton(String beanName, Object singletonObj) {
        Object oldVal = this.singletons.get(beanName);
        if (oldVal != null) {
            throw new ReregisterSingletonException("Bean name has already been registered, " +
                    "old value is:" + oldVal + "new value is:" + singletonObj);
        }
        this.singletons.put(beanName, singletonObj);
    }

    public Object getSingleton(String beanName) {
        if (this.registeredSingletons.contains(beanName)) {
            return this.singletons.get(beanName);
        }
        return null;
    }

    public boolean containsSingleton(String beanName) {
        return this.registeredSingletons.contains(beanName);
    }

    public String[] getSingletonNames() {
        return StringUtils.toStringArray(this.registeredSingletons);
    }

    public int getSingletonCount() {
        return this.registeredSingletons.size();
    }

    /**
     * dependentBean depends on bean.
     *
     * @param beanName          the name of the bean
     * @param dependentBeanName the name of the dependent bean
     */
    public void registerDependency(String beanName, String dependentBeanName) {
        logger.debug("register dependency for " + dependentBeanName + " -> " + beanName);
        Set<String> dependentBeanNames = this.dependentBeanMap.get(beanName);
        if (dependentBeanNames == null) {
            dependentBeanNames = new LinkedHashSet<String>();
            this.dependentBeanMap.put(beanName, dependentBeanNames);
        }
        dependentBeanNames.add(dependentBeanName);

        Set<String> dependencyForBeanNames = this.dependenciesForBeanMap.get(dependentBeanName);
        if (dependencyForBeanNames == null) {
            dependencyForBeanNames = new LinkedHashSet<String>();
            this.dependenciesForBeanMap.put(dependentBeanName, dependencyForBeanNames);
        }
        dependencyForBeanNames.add(beanName);
    }

    /**
     * @see BeanDependencyRegistry#getDependentBeans(String)
     */
    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (null == dependentBeans || dependentBeans.size() == 0) {
            return new String[0];
        }
        return dependentBeans.toArray(new String[dependentBeans.size()]);
    }

    /**
     * @see BeanDependencyRegistry#getDependenciesForBean(String)
     */
    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (null == dependenciesForBean || dependenciesForBean.size() == 0) {
            return new String[0];
        }
        return dependenciesForBean.toArray(new String[dependenciesForBean.size()]);
    }
}
