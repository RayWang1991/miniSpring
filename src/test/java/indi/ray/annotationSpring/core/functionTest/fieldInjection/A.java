package indi.ray.annotationSpring.core.functionTest.fieldInjection;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class A {
    @Autowired
    Object b;
}