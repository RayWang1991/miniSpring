package indi.ray.miniSpring.aop.proxy;

/**
 * Interface that are able to create Aop proxies based on {@link AdvisedSupport} configs
 * <p>Proxies should observe the following contract:
 * 1. They should implement all interfaces that the config indicates should be proxied
 * 2. They should implement the {@link Advised} interface
 * 3. They should implement the equals method to compare proxied interfaces, advice, and target.
 * 4. They should be serializable if all advisors and target are serializable.
 * 5. They should be thread-safe if all advisors and target are thread-safe.
 * </p>
 */
public interface AopProxyFactory {

    AopProxy createAopProxy(AdvisedSupport config);
}
