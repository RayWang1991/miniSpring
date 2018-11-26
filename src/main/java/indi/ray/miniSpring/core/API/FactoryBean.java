package indi.ray.miniSpring.core.API;

public interface FactoryBean {

    Object getObject();

    boolean isSingleton();
}
