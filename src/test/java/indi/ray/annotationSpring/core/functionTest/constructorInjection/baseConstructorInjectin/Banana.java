package indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin;

import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class Banana implements Fruit {
    @Override
    public String toString() {
        return "Banana{}";
    }
}
