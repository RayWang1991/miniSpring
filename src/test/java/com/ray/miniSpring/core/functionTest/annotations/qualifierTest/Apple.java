package com.ray.miniSpring.core.functionTest.annotations.qualifierTest;

import com.ray.miniSpring.core.annotations.Component;

@Component("pingAn")
public class Apple implements Fruit{
    @Override
    public String toString() {
        return "Student{}";
    }
}
