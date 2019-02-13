package com.ray.miniSpring.core.utils;

import java.lang.reflect.Field;

public class BeanUtils {
    private static final String GET_PREFIX = "get";
    private static final String IS_PREFIX  = "is";
    private static final String SET_PREFIX = "set";

    public static String genReaderName(Field field) {
        if (isBooleanClass(field.getType())) {
            return IS_PREFIX + StringUtils.capitalize(field.getName());
        } else {
            return GET_PREFIX + StringUtils.capitalize(field.getName());
        }
    }

    public static String genWriterName(Field field) {
        return SET_PREFIX + StringUtils.capitalize(field.getName());
    }

    private static boolean isBooleanClass(Class<?> clazz) {
        return clazz == Boolean.TYPE || Boolean.class.isAssignableFrom(clazz);
    }
}
