package com.ray.miniSpring.core.functionTest.annotations.constructorInjection.selfCirCular;


import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;

@Component(lazyInit = true)
public class SelfDependentBean {
    private Object selfDependentBean;

    @Autowired
    public SelfDependentBean(SelfDependentBean bean) {
        this.selfDependentBean = bean;
    }

    public Object getSelfDependentBean() {
        return selfDependentBean;
    }
}
