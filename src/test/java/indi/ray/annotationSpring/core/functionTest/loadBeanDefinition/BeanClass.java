package indi.ray.annotationSpring.core.functionTest.loadBeanDefinition;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.annotations.Primary;

@Component
@Primary
public class BeanClass {
    private int              a;
    @Autowired(required = false)
    private AnotherBeanClass bean;

    @Autowired
    public BeanClass(AnotherBeanClass bean) {
        this.bean = bean;
    }
}
