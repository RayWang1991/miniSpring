package indi.ray.annotationSpring.core.functionTest.fieldInjection;


import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class SelfDependentBean {
    @Autowired
    private Object selfDependentBean;

    public Object getSelfDependentBean() {
        return selfDependentBean;
    }
}
