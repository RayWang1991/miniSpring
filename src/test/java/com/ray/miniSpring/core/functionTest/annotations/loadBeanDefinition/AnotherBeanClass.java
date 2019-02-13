package com.ray.miniSpring.core.functionTest.annotations.loadBeanDefinition;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;

@Component
public class AnotherBeanClass {
    @Autowired
    private BeanClass bean;
}
