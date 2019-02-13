package com.ray.miniSpring.aop.advise;

import java.lang.reflect.Method;

public interface MethodInvocation extends Invocation {

    /**
     * Get the method to be invoked
     *
     * @return
     */
    Method getMethod();

}
