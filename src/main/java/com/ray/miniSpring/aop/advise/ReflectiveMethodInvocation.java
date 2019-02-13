package com.ray.miniSpring.aop.advise;

import com.ray.miniSpring.aop.utils.AopUtils;
import com.ray.miniSpring.aop.utils.InterceptorAndDynamicMethodMatcher;

import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements ProxyMethodInvocation {
    private List<?> interceptorsAndDynamicMethodMatchers;

    private int currentIndex;

    private Object target;

    private Class<?> targetClass;

    private Method method;

    private Object[] arguments;

    public ReflectiveMethodInvocation(List<?> interceptorsAndDynamicMethodMatchers, Object target, Method method, Object[] arguments) {
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    public Object proceed() throws Throwable {
        // start at 0, meaning next interceptor to match

        if (this.interceptorsAndDynamicMethodMatchers.isEmpty()
                || this.currentIndex == this.interceptorsAndDynamicMethodMatchers.size()) {
            return AopUtils.invokeMethodUsingReflective(method, target, arguments);
        }

        Object raw = this.interceptorsAndDynamicMethodMatchers.get(currentIndex);
        currentIndex++;

        if (raw instanceof MethodInterceptor) {
            MethodInterceptor methodInterceptor = (MethodInterceptor) raw;
            // just call it
            return methodInterceptor.invoke(this);
        } else {
            InterceptorAndDynamicMethodMatcher idm = (InterceptorAndDynamicMethodMatcher) raw;
            if (idm.getMatcher().matches(method, arguments)) {
                MethodInterceptor methodInterceptor = idm.getInterceptor();
                return methodInterceptor.invoke(this);
            } else {
                return this.proceed();
            }
        }
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }


}
