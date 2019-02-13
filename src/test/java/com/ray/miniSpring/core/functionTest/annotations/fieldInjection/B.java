package com.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.Autowired;

@Component
public class B {
    @Autowired
     Object c;
}
