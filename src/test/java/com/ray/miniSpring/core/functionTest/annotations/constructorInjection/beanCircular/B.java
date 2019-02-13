package com.ray.miniSpring.core.functionTest.annotations.constructorInjection.beanCircular;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;

@Component(lazyInit = true)
public class B {
    Object c;

    @Autowired
    public B(C c) {
        this.c = c;
    }
}
