package com.ray.miniSpring.aop;

public class Student extends AbstractPeople implements Studier {

    public void study() {
        System.out.println("I'm now studying");
    }

    public void say() {
        System.out.println("I'm a student");
    }
}
