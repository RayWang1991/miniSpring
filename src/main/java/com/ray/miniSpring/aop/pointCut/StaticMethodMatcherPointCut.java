package com.ray.miniSpring.aop.pointCut;

/**
 * Convenient superclass when we want to force subclass to implement the MethodMatcher interface
 * but want to be pointcuts.
 * <p>
 * The classFilter property can be set to customize behavior. The default is {@link ClassFilter#TRUE}
 */

public abstract class StaticMethodMatcherPointCut extends StaticMethodMatcher implements PointCut {
    private ClassFilter classFilter = ClassFilter.TRUE;

    public void setClassFilter(ClassFilter classFilter) {
        this.classFilter = classFilter;
    }

    public ClassFilter getClassFilter() {
        return classFilter;
    }

    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
