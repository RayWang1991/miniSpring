package com.ray.miniSpring.aop.pointCut;

import java.lang.reflect.Method;

public class TrueMethodMatcher extends StaticMethodMatcher {
    // singleton instance
    public static final TrueMethodMatcher INSTANCE = new TrueMethodMatcher();

    // suppress default constructor
    private TrueMethodMatcher() {
    }

    @Override
    public boolean matches(Method method) {
        return true;
    }
}
