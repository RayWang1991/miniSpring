package indi.ray.miniSpring.core.functionTest.annotations.constructorInjection.beanCircular;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component(lazyInit = true)
public class B {
    Object c;

    @Autowired
    public B(C c) {
        this.c = c;
    }
}
