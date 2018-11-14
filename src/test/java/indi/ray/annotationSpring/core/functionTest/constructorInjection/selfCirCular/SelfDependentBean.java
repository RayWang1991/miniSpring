package indi.ray.annotationSpring.core.functionTest.constructorInjection.selfCirCular;


import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

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
