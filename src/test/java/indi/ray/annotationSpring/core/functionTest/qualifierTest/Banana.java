package indi.ray.annotationSpring.core.functionTest.qualifierTest;

import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class Banana implements Fruit{
    @Override
    public String toString() {
        return "Banana{}";
    }
}
