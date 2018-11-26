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
        boolean hasContent = false;
        for (Object obj : array) {
            hasContent = true;
            sb.append(obj.toString()).append(",");
        }
        if (hasContent) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }
}
