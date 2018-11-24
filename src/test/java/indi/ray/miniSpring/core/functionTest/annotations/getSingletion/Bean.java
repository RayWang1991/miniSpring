package indi.ray.miniSpring.core.functionTest.annotations.getSingletion;

import indi.ray.miniSpring.core.annotations.Component;

@Component
public class Bean {
    public void serve() {
        System.out.println("bean is serving");
    }
}
