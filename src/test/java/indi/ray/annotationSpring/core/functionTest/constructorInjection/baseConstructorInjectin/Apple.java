package indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin;

import indi.ray.annotationSpring.core.annotations.Component;

@Component("pingAn")
public class Apple implements Fruit {
    @Override
    public String toString() {
        return "Apple{}";
    }
}
