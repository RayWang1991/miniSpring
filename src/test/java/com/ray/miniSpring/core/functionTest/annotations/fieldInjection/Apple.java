package com.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import com.ray.miniSpring.core.annotations.Component;

@Component("pingAn")
public class Apple implements Fruit{
    @Override
    public String toString() {
        return "Student{}";
    }
}
