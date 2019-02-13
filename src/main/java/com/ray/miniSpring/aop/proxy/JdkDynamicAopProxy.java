package com.ray.miniSpring.aop.proxy;

import com.ray.miniSpring.aop.utils.AopUtils;
import com.ray.miniSpring.aop.advise.ReflectiveMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advisedSupport = advised;
    }

    public Object getProxy() {
        return getProxy(Thread.currentThread().getContextClassLoader());
    }

    private Object getProxy(ClassLoader classLoader) {
        Class<?>[] interfaces = AopUtils.completeInterfaces(this.advisedSupport);
        return Proxy.newProxyInstance(classLoader, interfaces, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object oldProxy = null;
        Object target;
        Class<?> targetClass;
        boolean exposeProxy = this.advisedSupport.isExposeProxy();

        try {
            if (method.getDeclaringClass().isInterface()
                    && method.getDeclaringClass().isAssignableFrom(Advised.class)) {
                return AopUtils.invokeMethodUsingReflective(method, this.advisedSupport, args);
            }
            Object retVal;

            if (exposeProxy) {
                oldProxy = AopContext.setCurrentProxy(proxy);
            }
            target = this.advisedSupport.getTargetSource().getTarget();
            targetClass = target.getClass();

            List<Object> interceptorsOrDynamicMethodMatchers =
                    this.advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(method);
            if (interceptorsOrDynamicMethodMatchers.isEmpty()) {
                retVal = AopUtils.invokeMethodUsingReflective(method, target, args);
            } else {
                //todo
                ReflectiveMethodInvocation methodInvocation =
                        new ReflectiveMethodInvocation(interceptorsOrDynamicMethodMatchers, target, method, args);
                retVal = methodInvocation.proceed();
            }

            return retVal;

        } finally {
            if (exposeProxy) {
                AopContext.setCurrentProxy(oldProxy);
            }
        }
    }
}
