package indi.ray.annotationSpring.core.functionTest.fieldInjection;

import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class Banana implements Fruit{
    @Override
    public String toString() {
        return "Banana{}";
    }
}
