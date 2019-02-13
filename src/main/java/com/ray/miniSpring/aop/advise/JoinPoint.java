package com.ray.miniSpring.aop.advise;

public interface JoinPoint {

    /**
     * Proceed to the next interceptor in the chain
     * @return
     * @throws Throwable
     */
    Object proceed() throws Throwable;

}
