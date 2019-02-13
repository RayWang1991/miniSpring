package com.ray.miniSpring.core.API;

public interface FactoryBean {

    Object getObject();

    boolean isSingleton();
}
