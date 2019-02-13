package com.ray.miniSpring.aop;

public class Man extends AbstractPeople {
    {
        this.isFemale = false;
    }

    public void say() {
        System.out.println("Man's speaking");
    }

}
