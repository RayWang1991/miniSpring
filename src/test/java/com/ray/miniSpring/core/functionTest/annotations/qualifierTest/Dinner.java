package com.ray.miniSpring.core.functionTest.annotations.qualifierTest;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;
import com.ray.miniSpring.core.annotations.Qualifier;

@Component
public class Dinner {
    @Autowired
    @Qualifier("pingAn")
    private Fruit apple;

    @Autowired
    private Fruit banana;

    @Override
    public String toString() {
        return "Dinner{" +
                "apple=" + apple +
                ", banana=" + banana +
                '}';
    }
}
