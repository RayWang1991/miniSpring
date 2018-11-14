package indi.ray.annotationSpring.core.functionTest.loadBeanDefinition;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class AnotherBeanClass {
    @Autowired
    private BeanClass bean;
}
