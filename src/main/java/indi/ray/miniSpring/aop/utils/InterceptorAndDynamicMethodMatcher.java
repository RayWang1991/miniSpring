package indi.ray.miniSpring.aop.utils;

import indi.ray.miniSpring.aop.advise.Interceptor;
import indi.ray.miniSpring.aop.advise.MethodInterceptor;
import indi.ray.miniSpring.aop.pointCut.MethodMatcher;

import java.util.regex.Matcher;

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
