package indi.ray.annotationSpring.core.functionTest.getSingletion;

import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class Bean {
    public void serve() {
        System.out.println("bean is serving");
    }
}
