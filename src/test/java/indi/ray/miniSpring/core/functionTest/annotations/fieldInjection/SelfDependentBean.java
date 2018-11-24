package indi.ray.miniSpring.core.functionTest.annotations.fieldInjection;


import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component
public class SelfDependentBean {
    @Autowired
    private Object selfDependentBean;

    public Object getSelfDependentBean() {
        return selfDependentBean;
    }
}
