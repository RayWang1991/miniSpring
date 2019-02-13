package com.ray.miniSpring.aop.utils;

import com.ray.miniSpring.core.utils.AssertUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

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

    public static Class<?>[] getAllInterfacesForClass(Class<?> clz) {
        AssertUtils.assertNotNull(clz, "class should not be null");
        Set<Class<?>> interfaces = getAllInterfacesForClassAsSet(clz);
        return interfaces.toArray(new Class<?>[interfaces.size()]);
    }

    public static Set<Class<?>> getAllInterfacesForClassAsSet(Class<?> clz) {
        Set<Class<?>> interfaceSet = new HashSet<Class<?>>();

        boolean shouldPropagateToSuper = true;
        if (clz.isInterface()) {
            shouldPropagateToSuper = false;
            interfaceSet.add(clz);
        }

        if (shouldPropagateToSuper) {
            Class<?> clazz = clz;
            while (clazz != null) {
                for (Class<?> intClz : clazz.getInterfaces()) {
                    interfaceSet.addAll(getAllInterfacesForClassAsSet(intClz));
                }
                clazz = clazz.getSuperclass();
            }
        } else {
            for (Class<?> intClz : clz.getInterfaces()) {
                interfaceSet.addAll(getAllInterfacesForClassAsSet(intClz));
            }
        }
        return interfaceSet;
    }
}
