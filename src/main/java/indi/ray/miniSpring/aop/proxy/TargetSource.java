package indi.ray.miniSpring.aop.proxy;

public interface TargetSource {
    Object getTarget();

    Class<?> getTargetClass();

    boolean isSingleton();
}
