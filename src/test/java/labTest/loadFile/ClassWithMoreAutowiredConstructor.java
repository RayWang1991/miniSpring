package labTest.loadFile;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class ClassWithMoreAutowiredConstructor {
    @Autowired
    public ClassWithMoreAutowiredConstructor(String a) {
    }

    @Autowired
    public ClassWithMoreAutowiredConstructor(int a) {
    }
}
