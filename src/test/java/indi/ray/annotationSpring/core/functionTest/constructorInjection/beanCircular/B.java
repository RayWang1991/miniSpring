package indi.ray.annotationSpring.core.functionTest.constructorInjection.beanCircular;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component(lazyInit = true)
public class B {
    Object c;

    @Autowired
    public B(C c) {
        this.c = c;
    }
}
