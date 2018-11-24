package indi.ray.miniSpring.aop.pointCut;

public class TruePointCut implements PointCut {

    public static final TruePointCut INSTANCE = new TruePointCut();

    // suppress default constructor
    private TruePointCut() {
    }

    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    public MethodMatcher getMethodMatcher() {
        return MethodMatcher.TRUE;
    }
}
