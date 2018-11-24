package indi.ray.miniSpring.aop.proxy;

public abstract class AopContext {
    private static final ThreadLocal<Object> currentProxy = new ThreadLocal<Object>();

    public static Object getCurretnProxy() {
        return currentProxy.get();
    }

    public static Object setCurrentProxy(Object proxy) {
        Object oldVal = currentProxy.get();
        if (proxy == null) {
            currentProxy.remove();
        } else {
            currentProxy.set(proxy);
        }
        return oldVal;
    }
}
