package com.ray.miniSpring.core.utils;

import com.ray.miniSpring.core.API.Ordered;

import java.util.Comparator;

public class OrderUtils {
    public static final Comparator<Object> DEFALT_COMPARATOR = new DefaultComparator();

    private static class DefaultComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            boolean isOrderd1 = o1 instanceof Ordered;
            boolean isOrderd2 = o2 instanceof Ordered;
            if (isOrderd1 && !isOrderd2) {
                return -1;
            }
            if (!isOrderd1 && isOrderd2) {
                return 1;
            }
            if (isOrderd1 && isOrderd2) {
                return ((Ordered) o1).getOrder() - ((Ordered) o2).getOrder();
            } else {
                return 0;
            }
        }
    }
}
