package com.ray.miniSpring.core.functionTest.annotations.constructorInjection.baseConstructorInjectin;

import com.ray.miniSpring.core.annotations.Component;

@Component
public class Banana implements Fruit {
    @Override
    public String toString() {
        return "Banana{}";
    }
}
