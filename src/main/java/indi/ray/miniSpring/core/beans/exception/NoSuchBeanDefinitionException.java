package indi.ray.miniSpring.core.beans.exception;

/**
 * Exception When {@code BeanFactory} is asked for a bean instance for which it cannot find a definition
 */

public class NoSuchBeanDefinitionException extends BeansException {
    private String beanName;
    // todo type


    public NoSuchBeanDefinitionException(String beanName, String msg) {
        super("No bean named '" + beanName + "' available: " + msg);
        this.beanName = beanName;
    }

    public NoSuchBeanDefinitionException(String beanName) {
        super("No bean named '" + beanName + "' available");
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
