package com.ray.miniSpring.core.functionTest.annotations.constructorInjection.beanCircular;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;

@Component(lazyInit = true)
public class A {
    @Autowired
    Object b;
}
