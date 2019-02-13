package com.ray.miniSpring.aop.utils;

import com.ray.miniSpring.aop.advise.MethodInterceptor;
import com.ray.miniSpring.aop.pointCut.MethodMatcher;

public class InterceptorAndDynamicMethodMatcher {
    private MethodInterceptor interceptor;

    private MethodMatcher matcher;

    public InterceptorAndDynamicMethodMatcher(MethodInterceptor interceptor, MethodMatcher matcher) {
        this.interceptor = interceptor;
        this.matcher = matcher;
    }

    public MethodInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public MethodMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(MethodMatcher matcher) {
        this.matcher = matcher;
    }
}
