package indi.ray.annotationSpring.core.functionTest.qualifierTest;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.annotations.Qualifier;

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
