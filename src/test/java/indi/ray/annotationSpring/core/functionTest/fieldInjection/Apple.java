package indi.ray.annotationSpring.core.functionTest.fieldInjection;

import indi.ray.annotationSpring.core.annotations.Component;

@Component("pingAn")
public class Apple implements Fruit{
    @Override
    public String toString() {
        return "Apple{}";
    }
}
