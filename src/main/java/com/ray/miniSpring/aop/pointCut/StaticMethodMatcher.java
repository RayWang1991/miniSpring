package com.ray.miniSpring.aop.pointCut;

import java.lang.reflect.Method;

public abstract class StaticMethodMatcher implements MethodMatcher {
    public boolean isRuntime() {
        return false;
    }

    public boolean matches(Method method) {
        return false;
    }

    public boolean matches(Method method, Object... args) {
        // should never invoke this
        throw new UnsupportedOperationException();
    }
}
