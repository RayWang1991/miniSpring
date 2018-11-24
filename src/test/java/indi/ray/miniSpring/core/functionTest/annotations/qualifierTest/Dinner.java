package indi.ray.miniSpring.core.functionTest.annotations.qualifierTest;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;
import indi.ray.miniSpring.core.annotations.Qualifier;

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
