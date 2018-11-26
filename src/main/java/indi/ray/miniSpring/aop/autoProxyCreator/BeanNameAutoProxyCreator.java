package indi.ray.miniSpring.aop.autoProxyCreator;

import indi.ray.miniSpring.aop.advise.MethodInterceptor;
import indi.ray.miniSpring.aop.advise.MethodInvocation;
import indi.ray.miniSpring.aop.advisor.Advisor;
import indi.ray.miniSpring.aop.advisor.DefaultPointCutAdvisor;
import indi.ray.miniSpring.aop.proxy.ProxyCreatorSupport;
import indi.ray.miniSpring.aop.utils.ReflectiveUtils;
import indi.ray.miniSpring.core.API.BeanFactoryAware;
import indi.ray.miniSpring.core.API.BeanPostProcessor;
import indi.ray.miniSpring.core.API.Ordered;
import indi.ray.miniSpring.core.beans.exception.BeansException;
import indi.ray.miniSpring.core.beans.factory.BeanFactory;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BeanNameAutoProxyCreator implements BeanFactoryAware, BeanPostProcessor, Ordered {
    private static final Logger logger = Logger.getLogger(BeanNameAutoProxyCreator.class);

    private List<String> interceptorNames = new ArrayList<String>();

    private List<String> beanNames = new ArrayList<String>();

    private boolean resolvedAdvisors = false;

    private List<Advisor> advisors = new ArrayList<Advisor>();

    private BeanFactory beanFactory;

    public void setInterceptorNames(List<String> interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setBeanNames(List<String> beanNames) {
        this.beanNames = beanNames;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanNames.contains(beanName)) {
            try {
                Object proxy = genProxy(bean);
                logger.info("auto gen proxy for " + beanName);
                bean = proxy;
            } catch (Exception e) {
                logger.error("auto create proxy for " + beanName + " failed, reason: " + e);
            }
        }
        return bean;
    }

    private Object genProxy(Object target) {
        resolveAdvisorsIfNeeded();
        ProxyCreatorSupport config = new ProxyCreatorSupport();
        config.setTarget(target);
        config.setInterfaces(ReflectiveUtils.getAllInterfacesForClass(target.getClass()));
        for (Advisor advisor : this.advisors) {
            config.addAdvisor(advisor);
        }
        return config.createAopProxy().getProxy();
    }

    private void resolveAdvisorsIfNeeded() {
        if (resolvedAdvisors) {
            return;
        }

        AssertUtils.assertNotNull(this.beanFactory, "beanFactory can not be null when resolve advisors");

        for (String name : this.interceptorNames) {
            Object interceptor = this.beanFactory.getBean(name);
            if (interceptor instanceof Advisor) {
                advisors.add((Advisor) interceptor);
            } else {
                AssertUtils.assertTrue(interceptor instanceof MethodInterceptor, "Only Method Interceptor is supported");
                advisors.add(new DefaultPointCutAdvisor((MethodInterceptor) interceptor));
            }
        }

        resolvedAdvisors = true;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public int getOrder() {
        return LOWEST_ORDER;
    }


}
