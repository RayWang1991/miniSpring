package indi.ray.miniSpring.core.functionTest.annotations.constructorInjection.beanCircular;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component(lazyInit = true)
public class A {
    @Autowired
    Object b;
}