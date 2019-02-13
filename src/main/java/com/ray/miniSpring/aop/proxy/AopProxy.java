package com.ray.miniSpring.aop.proxy;

/**
 * This interface generates real proxySetUp, e.g a jdk proxySetUp or a CGLIB proxySetUp
 */
public interface AopProxy {

    /**
     * generate a new proxySetUp object using the default classloader
     *
     * @return
     */
    Object getProxy();

}
