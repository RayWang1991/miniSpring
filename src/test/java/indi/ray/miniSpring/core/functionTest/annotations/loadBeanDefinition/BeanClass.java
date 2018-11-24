package indi.ray.miniSpring.core.functionTest.annotations.loadBeanDefinition;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;
import indi.ray.miniSpring.core.annotations.Primary;

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
