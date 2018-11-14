package indi.ray.annotationSpring.core.functionTest.qualifierTest;

import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.annotations.Qualifier;

@Component("pingAn")
public class Apple implements Fruit{
    @Override
    public String toString() {
        return "Apple{}";
    }
}
