package com.ray.miniSpring.aop.proxy;

import com.ray.miniSpring.aop.exceptions.AopConfigEception;
import com.ray.miniSpring.aop.utils.AopUtils;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.aop.advisor.Advisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdvisedSupport extends ProxyConfig implements Advised {
    private List<Class<?>> interfaceList = new ArrayList<Class<?>>();

    private List<Advisor> advisorList = new ArrayList<Advisor>();

    private TargetSource targetSource;

    private Map<Method, List<Object>> methodCacheForInterceptors = new ConcurrentHashMap<Method, List<Object>>();

    /**
     * 生产Aop代理之前调用，校验设置合法性
     */
    public void validate() {
        AssertUtils.assertNotNull(this.targetSource, "target source can not be null");
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method) {
        List<Object> cached = this.methodCacheForInterceptors.get(method);
        if (cached == null) {
            cached = AopUtils.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetSource.getTargetClass());
            this.methodCacheForInterceptors.put(method, cached);
        }
        return cached;
    }

    public void setInterfaces(Class<?>... interfaces) {
        AssertUtils.assertNotNull(interfaces, "不允许设置interfaces为空");
        this.interfaceList.clear();
        for (Class<?> clazz : interfaces) {
            this.interfaceList.add(clazz);
        }
    }

    public void addInterface(Class<?> interfaceClass) {
        AssertUtils.assertNotNull(interfaceClass, "interfaceClass不允许为空");
        if (!interfaceClass.isInterface()) {
            throw new AopConfigEception(interfaceClass.getName() + "不是interface类型");
        }
        if (!this.interfaceList.contains(interfaceClass)) {
            this.interfaceList.add(interfaceClass);
        }
    }

    public Class<?>[] getProxiedInterfaces() {
        return interfaceList.toArray(new Class[interfaceList.size()]);
    }

    public void setTarget(Object target) {
        this.setTargetSource(new SingletonTargetSource(target));
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public TargetSource getTargetSource() {
        return this.targetSource;
    }

    public boolean isInterfaceProxied(Class<?> interfaceClass) {
        for (Class<?> clazz : this.interfaceList) {
            if (interfaceClass.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    protected void setAdvisorList(List<Advisor> advisorList) {
        AssertUtils.assertNotNull(advisorList, "advisorList should not be null");
        this.advisorList = advisorList;
    }

    protected List<Advisor> getAdvisorList() {
        return advisorList;
    }

    public Advisor[] getAdvisors() {
        return this.advisorList.toArray(new Advisor[this.advisorList.size()]);
    }

    public void addAdvisor(Advisor advisor) {
        this.advisorList.add(advisor);
    }

    public void addAdvisor(Advisor advisor, int index) {
        this.advisorList.add(index, advisor);
    }

    public void removeAdvisor(int index) {
        this.advisorList.remove(index);
    }
}
