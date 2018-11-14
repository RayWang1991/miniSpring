package indi.ray.annotationSpring.core.utils;

import java.util.Collection;
import java.util.Collections;

public class StringUtils {
    /**
     * Copy the given {@code collection} to a {@code String} array.
     *
     * @param collection the collection to copy
     * @return the String array
     */
    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return new String[0];
        }
        return collection.toArray(new String[collection.size()]);
    }

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (capitalize) {
            sb.append(Character.toUpperCase(str.charAt(0)));
        } else {
            sb.append(Character.toLowerCase(str.charAt(0)));
        }
        sb.append(str.substring(1));
        return sb.toString();
    }

    public static String unCapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }
}
