package com.ray.miniSpring.core.beans.exception;

public class BeansException extends RuntimeException {

    /**
     * create a new BeansException with the specified message
     * @param msg
     */
    public BeansException(String msg) {
        super(msg);
    }
}
