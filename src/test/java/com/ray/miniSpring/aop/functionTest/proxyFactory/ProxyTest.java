package com.ray.miniSpring.aop.functionTest.proxyFactory;

import com.ray.miniSpring.aop.functionTest.proxySetUp.MethodLoggerInterceptor;
import com.ray.miniSpring.aop.functionTest.proxySetUp.MethodPerformanceInterceptor;
import com.ray.miniSpring.aop.proxy.ProxyFactory;
import com.ray.miniSpring.aop.proxy.SingletonTargetSource;
import com.ray.miniSpring.aop.Man;
import com.ray.miniSpring.aop.People;
import com.ray.miniSpring.aop.advise.MethodInterceptor;
import com.ray.miniSpring.aop.advisor.NamedMatchMethodPointCutAdvisor;
import com.ray.miniSpring.aop.advisor.RegexMethodPointCutAdvisor;
import com.ray.miniSpring.aop.proxy.AdvisedSupport;
import com.ray.miniSpring.aop.proxy.AopProxyFactory;
import com.ray.miniSpring.aop.proxy.DefaultAopProxyFactory;
import org.junit.Test;

public class ProxyTest {
    @Test
    public void testUsingBasicComponent() {
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

        // proxySetUp instance
        Object proxy = aopProxyFactory.createAopProxy(config).getProxy();

        People man = (People) proxy;
        man.say();
        man.getName();
        man.getAge();
    }

    @Test
    public void testUsingProxyFactory() {
        // MethodInterceptor
        MethodInterceptor performanceInterceptor = new MethodPerformanceInterceptor();

        // target
        Man targetMan = new Man();
        targetMan.setName("TOTO");
        targetMan.setAge(10);

        // proxySetUp instance
        People man = ProxyFactory.getProxy(People.class, targetMan, performanceInterceptor);

        man.say();
        System.out.println(man.getName());
        System.out.println(man.getAge());
    }
}
