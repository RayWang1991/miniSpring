package indi.ray.miniSpring.aop.pointCut;

public class TrueClassFilter implements ClassFilter {

    public static final TrueClassFilter INSTANCE = new TrueClassFilter();

    /**
     * invalid normal constructor
     */
    private TrueClassFilter() {
    }

    /**
     * @param clazz
     * @return
     * @see ClassFilter#matches(Class)
     */
    public boolean matches(Class<?> clazz) {
        return true;
    }

}
