package indi.ray.miniSpring.core.functionTest.annotations.loadBeanDefinition;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component
public class AnotherBeanClass {
    @Autowired
    private BeanClass bean;
}
