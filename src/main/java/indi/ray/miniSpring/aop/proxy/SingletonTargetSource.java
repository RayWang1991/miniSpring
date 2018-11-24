package indi.ray.miniSpring.aop.proxy;

public class SingletonTargetSource implements TargetSource {
    private Object target;

    public SingletonTargetSource(Object target) {
        this.target = target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public Class<?> getTargetClass() {
        return target.getClass();
    }
}
