package com.ray.miniSpring.core.functionTest.annotations.constructorInjection.baseConstructorInjectin;

import com.ray.miniSpring.core.annotations.Component;

@Component("pingAn")
public class Apple implements Fruit {
    @Override
    public String toString() {
        return "Student{}";
    }
}
