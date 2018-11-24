package indi.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import indi.ray.miniSpring.core.annotations.Component;

@Component
public class Banana implements Fruit{
    @Override
    public String toString() {
        return "Banana{}";
    }
}
