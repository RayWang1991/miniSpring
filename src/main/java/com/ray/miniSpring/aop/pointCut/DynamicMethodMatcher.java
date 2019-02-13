package com.ray.miniSpring.aop.pointCut;

import java.lang.reflect.Method;

public abstract class DynamicMethodMatcher implements MethodMatcher {
    public boolean isRuntime() {
        return true;
    }

    public boolean matches(Method method) {
        return true;
    }
}
