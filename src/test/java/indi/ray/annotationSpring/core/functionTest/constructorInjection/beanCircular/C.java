package indi.ray.annotationSpring.core.functionTest.constructorInjection.beanCircular;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component(lazyInit = true)
public class C {
    Object a;

    @Autowired
    public C(A a) {
        this.a = a;
    }

}
