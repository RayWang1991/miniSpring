package indi.ray.annotationSpring.core.functionTest.constructorInjection;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class A {
    @Autowired
    Object b;
}
