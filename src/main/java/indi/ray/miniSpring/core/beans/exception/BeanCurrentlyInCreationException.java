package indi.ray.miniSpring.core.beans.exception;

public class BeanCurrentlyInCreationException extends BeansException {
    public BeanCurrentlyInCreationException(String beanName) {
        super(beanName + " is currently in creation, please check if there is any circular dependency");
    }
}
