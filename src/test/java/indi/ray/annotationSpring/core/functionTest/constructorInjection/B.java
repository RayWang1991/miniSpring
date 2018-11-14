package indi.ray.annotationSpring.core.functionTest.constructorInjection;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class B {
    @Autowired
    Object c;

//    public B(Object c) {
//        this.c = c;
//    }
}
