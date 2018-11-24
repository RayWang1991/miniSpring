package indi.ray.miniSpring.aop.pointCut;

/**
 * Part of a PointCut:
 * checks that a class can be eligible for advice
 */
public interface ClassFilter {

    /**
     * Is the target class available to be applied on the given pointcut?
     *
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);

    /**
     * ClassFilter That matches all class
     */
    ClassFilter TRUE = TrueClassFilter.INSTANCE;
}
