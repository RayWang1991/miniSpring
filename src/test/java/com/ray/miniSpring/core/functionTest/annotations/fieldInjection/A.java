package com.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.Autowired;

@Component
public class A {
    @Autowired
    Object b;
}
