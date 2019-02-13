package com.ray.miniSpring.aop.proxy;

import com.ray.miniSpring.aop.advise.MethodInterceptor;
import com.ray.miniSpring.aop.utils.AopUtils;
import com.ray.miniSpring.aop.utils.ReflectiveUtils;

public class ProxyFactory extends ProxyCreatorSupport {
    public ProxyFactory(Object target) {
        this.setTarget(target);
        this.setInterfaces(ReflectiveUtils.getAllInterfacesForClass(this.getTargetSource().getTargetClass()));
    }

    public ProxyFactory(Class<?>... proxyInterfaces) {
        setInterfaces(proxyInterfaces);
    }

    public ProxyFactory(MethodInterceptor interceptor, Class<?>... proxyInterfaces) {
        addAdvisor(AopUtils.wrapAdvisorIfNeeded(interceptor));
        setInterfaces(proxyInterfaces);
    }

    public ProxyFactory(MethodInterceptor interceptor, Object target, Class<?>... proxyInterfaces) {
        addAdvisor(AopUtils.wrapAdvisorIfNeeded(interceptor));
        setInterfaces(proxyInterfaces);
        setTarget(target);
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    @SuppressWarnings("unchecked cast")
    public static <T> T getProxy(Class<T> proxyInterface, MethodInterceptor interceptor) {
        return (T) new ProxyFactory(interceptor, proxyInterface).getProxy();
    }


    @SuppressWarnings("unchecked cast")
    public static <T> T getProxy(Class<T> proxyInterface, Object target, MethodInterceptor interceptor) {
        return (T) new ProxyFactory(interceptor, target, proxyInterface).getProxy();
    }
}
