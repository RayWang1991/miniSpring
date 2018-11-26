package indi.ray.miniSpring.aop.proxy;

import indi.ray.miniSpring.aop.exceptions.AopConfigEception;

public class DefaultAopProxyFactory implements AopProxyFactory {
    public AopProxy createAopProxy(AdvisedSupport config) {
        config.validate();

        if (config.isProxyTargetClass() || hasNoUserDefinedInterfaces(config)) {
            return createProxyUsingCglib(config);
        } else {
            return createProxyUsingJdk(config);
        }
    }

    private boolean hasNoUserDefinedInterfaces(AdvisedSupport config) {
        Class<?>[] interfaces = config.getProxiedInterfaces();
        return interfaces.length == 0;
    }

    private AopProxy createProxyUsingJdk(AdvisedSupport config) {
        return new JdkDynamicAopProxy(config);
    }

    private AopProxy createProxyUsingCglib(AdvisedSupport config) {
        throw new AopConfigEception("Cglib proxySetUp is Unsupported now");
    }
}
