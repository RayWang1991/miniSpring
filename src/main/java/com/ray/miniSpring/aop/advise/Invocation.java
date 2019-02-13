package com.ray.miniSpring.aop.advise;

/**
 * This interface represents an invocation in program, e.g.
 * method invocation and constructor invocation
 */
public interface Invocation extends JoinPoint{

    /**
     * Get the arguments of the invocation in an array
     *
     * @return
     */
    Object[] getArguments();
}
