package com.ray.miniSpring.core.functionTest.annotations.fieldInjection;


import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.Autowired;

@Component
public class SelfDependentBean {
    @Autowired
    private Object selfDependentBean;

    public Object getSelfDependentBean() {
        return selfDependentBean;
    }
}
