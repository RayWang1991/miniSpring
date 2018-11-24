package indi.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component
public class B {
    @Autowired
     Object c;
}
