package com.ray.miniSpring.aop.proxy;

import com.ray.miniSpring.aop.advisor.Advisor;

public interface Advised {

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    void setTargetSource(TargetSource targetSource);

    TargetSource getTargetSource();

    boolean isInterfaceProxied(Class<?> interfaceClass);

    void setExposeProxy(boolean exposeProxy);

    boolean isExposeProxy();

    Advisor[] getAdvisors();

    void addAdvisor(Advisor advisor);

    void addAdvisor(Advisor advisor, int index);

    void removeAdvisor(int index);

}
