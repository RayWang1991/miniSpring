package indi.ray.miniSpring.aop.proxy;

/**
 * This interface generates real proxy, e.g a jdk proxy or a CGLIB proxy
 */
public interface AopProxy {

    /**
     * generate a new proxy object using the default classloader
     *
     * @return
     */
    Object getProxy();

}
