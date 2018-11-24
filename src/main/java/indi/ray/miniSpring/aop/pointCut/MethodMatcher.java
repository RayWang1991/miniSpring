package indi.ray.miniSpring.aop.pointCut;

import java.lang.reflect.Method;

public interface MethodMatcher {

    boolean isRuntime();

    boolean matches(Method method);

    boolean matches(Method method, Object... args);

    // method mather that matches all methods
    MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;
}
