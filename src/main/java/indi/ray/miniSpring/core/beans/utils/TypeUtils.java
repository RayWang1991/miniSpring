package indi.ray.miniSpring.core.beans.utils;

import indi.ray.miniSpring.core.beans.definition.ValueOrRef;
import indi.ray.miniSpring.core.beans.exception.BeansException;
import indi.ray.miniSpring.core.utils.AssertUtils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static indi.ray.miniSpring.core.beans.definition.Value.*;

public class TypeUtils {
    private static final Map<String, Class<?>> primaryTypeMap = new HashMap<String, Class<?>>();

    static {
        //todo
        primaryTypeMap.put("int", Integer.TYPE);
        primaryTypeMap.put("long", Long.TYPE);
        primaryTypeMap.put("char", Character.TYPE);
        primaryTypeMap.put("short", Short.TYPE);
        primaryTypeMap.put("double", Double.TYPE);
        primaryTypeMap.put("float", Float.TYPE);
        primaryTypeMap.put("boolean", Boolean.TYPE);
        primaryTypeMap.put("byte", Byte.TYPE);
    }

    public static Class<?> typeForName(String name) {
        Class<?> clazz = null;
        if ((clazz = primaryTypeMap.get(name)) != null) {
            return clazz;
        }
        try {
            clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new BeansException(e.toString());
        }
        return clazz;
    }

    // numbers or string
    public static boolean compatibleForInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean compatibleForDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean coompatibleForString(String value) {
        return true;
    }

    public static Object toWrappedValue(Class<?> typeToWrap, String raw) {
        if (typeToWrap == Integer.class || typeToWrap == Integer.TYPE) return Integer.valueOf(raw);
        if (typeToWrap == Float.class || typeToWrap == Float.TYPE) return Float.valueOf(raw);
        if (typeToWrap == Long.class || typeToWrap == Long.TYPE) return Long.valueOf(raw);
        if (typeToWrap == Double.class || typeToWrap == Double.TYPE) return Double.valueOf(raw);
        if (typeToWrap == Character.class || typeToWrap == Character.TYPE) return (char) (Integer.parseInt(raw));
        if (typeToWrap == Short.class || typeToWrap == Short.TYPE) return Short.valueOf(raw);
        if (typeToWrap == Byte.class || typeToWrap == Byte.TYPE) return Byte.valueOf(raw);
        if (typeToWrap == Boolean.class || typeToWrap == Boolean.TYPE) return Boolean.valueOf(raw);
        // String type
        return raw;
    }

    // constructor solver use
    public static boolean isTypeMatch(Class<?> typeToMatch, ValueOrRef valueOrRef) {
        if (valueOrRef.isValue()) {
            int typeOfValue = valueOrRef.getValueSubType();
            switch (typeOfValue) {
                case PLAIN_VALUE: {
                    Class<?>[] classesToTest = null;
                    if (TypeUtils.compatibleForInt(valueOrRef.getRawValue())) {
                        classesToTest = new Class[]{Integer.TYPE, Long.TYPE, Character.TYPE, Short.TYPE, Byte.TYPE};
                    } else if (TypeUtils.compatibleForDouble(valueOrRef.getRawValue())) {
                        classesToTest = new Class[]{Float.TYPE, Double.TYPE};
                    } else {
                        classesToTest = new Class[]{String.class};
                    }
                    for (Class<?> typeToTest : classesToTest) {
                        if (isTypeMatchPlain(typeToMatch, typeToTest)) return true;
                    }
                    return false;
                }
                case SET_VALUE: {
                    return isTypeMatchForSet(typeToMatch);
                }
                case LIST_VALUE: {
                    return isTypeMatchForList(typeToMatch, valueOrRef.getTypeForValue());
                }
                case NULL_VALUE:
                    return true;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            // ref for beans
            return isTypeMatchPlain(typeToMatch, valueOrRef.getRequiredType());
        }
    }


    public static boolean isTypeMatchPlain(Class<?> typeToMatch, Class<?> typeToTest) {
        AssertUtils.assertNotNull(typeToMatch, "type to match must not be null");
        AssertUtils.assertNotNull(typeToTest, "type to proxyFactory must not be null");
        if (typeToMatch.isAssignableFrom(typeToTest)) return true;
        if (typeToMatch.isPrimitive()) {
            return typeToMatch == typeToTest;
        }
        if (typeToTest.isPrimitive()) {
            if (typeToMatch == Integer.class && typeToTest == Integer.TYPE) return true;
            if (typeToMatch == Float.class && typeToTest == Float.TYPE) return true;
            if (typeToMatch == Long.class && typeToTest == Long.TYPE) return true;
            if (typeToMatch == Double.class && typeToTest == Double.TYPE) return true;
            if (typeToMatch == Character.class && typeToTest == Character.TYPE) return true;
            if (typeToMatch == Short.class && typeToTest == Short.TYPE) return true;
            if (typeToMatch == Byte.class && typeToTest == Byte.TYPE) return true;
            if (typeToMatch == Boolean.class && typeToTest == Boolean.TYPE) return true;
        }
        return false;
    }

    public static boolean isTypeMatchForList(Class<?> typeToMatch, Class<?> elementType) {
        // todo, 子元素检查
        if (typeToMatch.isArray()) {
            Class<?> componentType = typeToMatch.getComponentType();
            return componentType.isAssignableFrom(elementType);
        }
        // todo 泛型检查
        return List.class.isAssignableFrom(typeToMatch);
    }

    public static boolean isTypeMatchForSet(Class<?> typeToMatch) {
        return Set.class.isAssignableFrom(typeToMatch);
    }

}
