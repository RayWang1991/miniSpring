package com.ray.miniSpring.aop.proxy;

import com.ray.miniSpring.aop.advisor.Advisor;

import java.util.List;

public class ProxyCreatorSupport extends AdvisedSupport {
    private AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();

    public ProxyCreatorSupport() {
    }

    public ProxyCreatorSupport(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public AopProxy createAopProxy() {
        return this.aopProxyFactory.createAopProxy(this);
    }

    public static ProxyCreatorSupport copyFrom(ProxyCreatorSupport old, TargetSource targetSource, List<Advisor> advisors) {
        ProxyCreatorSupport copy = new ProxyCreatorSupport(old.aopProxyFactory);
        copy.setTargetSource(targetSource);
        copy.setAdvisorList(advisors);
        return copy;
    }
}
