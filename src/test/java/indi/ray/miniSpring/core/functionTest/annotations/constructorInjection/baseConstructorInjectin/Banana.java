package indi.ray.miniSpring.core.functionTest.annotations.constructorInjection.baseConstructorInjectin;

import indi.ray.miniSpring.core.annotations.Component;

@Component
public class Banana implements Fruit {
    @Override
    public String toString() {
        return "Banana{}";
    }
}
