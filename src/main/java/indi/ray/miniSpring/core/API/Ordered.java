package indi.ray.miniSpring.core.API;

public interface Ordered {
    
    int HIGHEST_ORDER = Integer.MIN_VALUE;

    int LOWEST_ORDER = Integer.MAX_VALUE;

    /**
     * return an int to represent order of the object.
     * Lower value represent a higher priority
     *
     * @return
     */
    int getOrder();
}
