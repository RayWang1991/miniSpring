package indi.ray.miniSpring.aop.proxy;

public class ProxyConfig {
    private boolean optimize         = false;
    private boolean exposeProxy      = false;
    private boolean proxyTargetClass = false;

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

    public boolean isExposeProxy() {
        return exposeProxy;
    }

    public void setExposeProxy(boolean exposeProxy) {
        this.exposeProxy = exposeProxy;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }
}
