package indi.ray.miniSpring.aop.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class ReflectiveUtils {
    private static final Logger logger = Logger.getLogger(ReflectiveUtils.class);

    /**
     * get method using {@link Class#getDeclaredMethod(String, Class[])} recursively
     */
    public static Method getMethodRecursively(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws
            NoSuchMethodException, SecurityException {
        Method method = null;
        for (Class<?> clz = clazz; clz != null; clz = clz.getSuperclass()) {
            try {
                method = clz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                logger.info(e);
                continue;
            }
            if (method != null) {
                break;
            }
        }
        if (method == null) {
            throw new NoSuchMethodException(clazz.getName() + "."
                    + methodName + "in its superclasses");
        }
        return method;
    }
}
