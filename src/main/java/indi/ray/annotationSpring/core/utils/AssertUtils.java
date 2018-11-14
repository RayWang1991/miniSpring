package indi.ray.annotationSpring.core.utils;

import java.util.concurrent.Executor;

public class AssertUtils {

    public static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertNotNull(Object obj, String msg) {
        if (null == obj) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertThrows(Class<? extends Exception> exceptionClazz, Executable executable) {
        try {
            executable.execute();
        } catch (Exception e) {
            assertTrue(exceptionClazz.isInstance(e), "异常类型不符,抛出" + e + ",期望" + exceptionClazz);
        }
    }
}
