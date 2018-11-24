package indi.ray.miniSpring.core.utils;

public class ArrayUtils {
    public static int getLength(Object[] array) {
        if (array == null) return 0;
        return array.length;
    }

    public static String genStringForObjArray(Object[] array) {
        if (null == array) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object obj : array) {
            sb.append(obj.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
