package indi.ray.miniSpring.core.utils;

public class ObjectUtils {
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        }
        return obj1.equals(obj2);
    }
}
