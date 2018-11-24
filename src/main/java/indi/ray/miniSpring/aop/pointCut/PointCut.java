package indi.ray.miniSpring.aop.pointCut;

public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

    PointCut TRUE = TruePointCut.INSTANCE;
}
