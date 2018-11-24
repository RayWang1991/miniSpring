package indi.ray.miniSpring.aop.functionTest.test;

import indi.ray.miniSpring.aop.Man;
import indi.ray.miniSpring.aop.People;
import indi.ray.miniSpring.aop.advise.MethodInterceptor;
import indi.ray.miniSpring.aop.advisor.NamedMatchMethodPointCutAdvisor;
import indi.ray.miniSpring.aop.advisor.RegexMethodPointCutAdvisor;
import indi.ray.miniSpring.aop.functionTest.proxy.MethodLoggerInterceptor;
import indi.ray.miniSpring.aop.functionTest.proxy.MethodPerformanceInterceptor;
import indi.ray.miniSpring.aop.proxy.AdvisedSupport;
import indi.ray.miniSpring.aop.proxy.AopProxy;
import indi.ray.miniSpring.aop.proxy.AopProxyFactory;
import indi.ray.miniSpring.aop.proxy.DefaultAopProxyFactory;
import indi.ray.miniSpring.aop.proxy.SingletonTargetSource;
import org.junit.Test;

public class ProxyTest {
    @Test
    public void test() {
        // MethodInterceptor
        MethodInterceptor logInterceptor = new MethodLoggerInterceptor();
        MethodInterceptor performanceInterceptor = new MethodPerformanceInterceptor();

        // logAdvisor
        RegexMethodPointCutAdvisor logAdvisor = new RegexMethodPointCutAdvisor();
        logAdvisor.setPatterns("get.*");
        logAdvisor.setAdvice(logInterceptor);

        // performance advisor
        NamedMatchMethodPointCutAdvisor performanceAdvisor = new NamedMatchMethodPointCutAdvisor();
        performanceAdvisor.setMappedNames("getAge", "say");
        performanceAdvisor.setAdvice(performanceInterceptor);

        // target
        Man targetMan = new Man();
        targetMan.setName("TOTO");
        targetMan.setAge(10);
        SingletonTargetSource targetSource = new SingletonTargetSource(targetMan);

        // config
        AdvisedSupport config = new AdvisedSupport();
        config.setInterfaces(People.class);
        config.setTargetSource(targetSource);
        config.setProxyTargetClass(false);
        config.addAdvisor(logAdvisor);
        config.addAdvisor(performanceAdvisor);

        // proxyFactory
        AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();

        // proxy instance
        Object proxy = aopProxyFactory.createAopProxy(config).getProxy();

        People man = (People) proxy;
        man.say();
        man.getName();
        man.getAge();
    }
}
