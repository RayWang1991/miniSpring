package indi.ray.miniSpring.aop.proxy;

import indi.ray.miniSpring.aop.advise.Advice;
import indi.ray.miniSpring.aop.advisor.Advisor;
import indi.ray.miniSpring.aop.utils.AopUtils;
import indi.ray.miniSpring.aop.utils.ReflectiveUtils;
import indi.ray.miniSpring.core.API.BeanFactoryAware;
import indi.ray.miniSpring.core.API.FactoryBean;
import indi.ray.miniSpring.core.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBean extends ProxyCreatorSupport implements FactoryBean, BeanFactoryAware {
    List<String> interceptorNames = new ArrayList<String>();

    Object target;

    private BeanFactory beanFactory;

    private Object cachedSingletonProxy;

    private List<Advisor> rawAdvisors = new ArrayList<Advisor>();

    private boolean autoDetectInterfaces = true;

    private boolean singleton = true;

    private boolean resolvedRawAdvisors = false;

    public List<String> getInterceptorNames() {
        return interceptorNames;
    }

    public void setInterceptorNames(List<String> interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
        if (target instanceof TargetSource) {
            this.setTargetSource((TargetSource) target);
        } else {
            this.setTargetSource(new SingletonTargetSource(target));
        }
    }

    public Object getObject() {
        if (this.singleton && this.cachedSingletonProxy != null) {
            return this.cachedSingletonProxy;
        }

        // have to create new proxySetUp

        // resolve names to advisors
        resolveRawAdvisorsIfNeeded();

        // auto detect interfaces
        if (this.autoDetectInterfaces || this.getProxiedInterfaces().length == 0) {
            Class<?>[] allInterfaces = ReflectiveUtils.getAllInterfacesForClass(this.getTargetSource().getTargetClass());
            this.setInterfaces(allInterfaces);
        }

        Object proxy;
        if (this.singleton) {
            proxy = createSingletonProxy();
            this.cachedSingletonProxy = proxy;
        } else {
            proxy = createPrototypeProxy();
        }

        return proxy;
    }

    private Object createSingletonProxy() {
        List<Advisor> advisorList = getAdvisorsFromRaw();
        this.setAdvisorList(advisorList);
        return getAopProxyFactory().createAopProxy(this).getProxy();
    }

    private Object createPrototypeProxy() {
        List<Advisor> advisorList = getAdvisorsFromRaw();
        this.setAdvisorList(advisorList);

        ProxyCreatorSupport copy = ProxyCreatorSupport.copyFrom(this, this.getTargetSource(), advisorList);
        return copy.getAopProxyFactory().createAopProxy(copy).getProxy();
    }


    private List<Advisor> getAdvisorsFromRaw() {
        List<Advisor> advisors;

        advisors = new ArrayList<Advisor>(this.rawAdvisors.size());
        for (Advisor advisor : this.rawAdvisors) {
            if (advisor instanceof PrototypeAdvisorHolder) {
                PrototypeAdvisorHolder prototypeAdvisorHolder = (PrototypeAdvisorHolder) advisor;
                Object advice = this.beanFactory.getBean(prototypeAdvisorHolder.name);
                advisors.add(AopUtils.wrapAdvisorIfNeeded(advice));
            } else {
                advisors.add(advisor);
            }
        }

        return advisors;
    }

    private void resolveRawAdvisorsIfNeeded() {
        if (resolvedRawAdvisors) {
            return;
        }

        for (String name : interceptorNames) {
            Advisor advisor;
            if (this.beanFactory.isSingleton(name) || this.isSingleton()) {
                Object bean = this.beanFactory.getBean(name);
                advisor = AopUtils.wrapAdvisorIfNeeded(bean);
            } else {
                advisor = new PrototypeAdvisorHolder(name);
            }
            this.rawAdvisors.add(advisor);
        }

        resolvedRawAdvisors = true;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isAutoDetectInterfaces() {
        return autoDetectInterfaces;
    }

    public void setAutoDetectInterfaces(boolean autoDetectInterfaces) {
        this.autoDetectInterfaces = autoDetectInterfaces;
    }

    private static class PrototypeAdvisorHolder implements Advisor {
        String name;

        PrototypeAdvisorHolder(String name) {
            this.name = name;
        }

        public Advice getAdvice() {
            throw new UnsupportedOperationException("can not invoke this method, this instance is to hold prototype for advisor " + name);
        }
    }
}
