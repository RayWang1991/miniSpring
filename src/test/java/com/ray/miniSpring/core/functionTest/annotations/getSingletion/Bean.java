package com.ray.miniSpring.core.functionTest.annotations.getSingletion;

import com.ray.miniSpring.core.annotations.Component;

@Component
public class Bean {
    public void serve() {
        System.out.println("bean is serving");
    }
}
