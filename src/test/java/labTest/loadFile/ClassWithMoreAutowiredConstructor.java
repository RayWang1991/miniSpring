package labTest.loadFile;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component
public class ClassWithMoreAutowiredConstructor {
    @Autowired
    public ClassWithMoreAutowiredConstructor(String a) {
    }

    @Autowired
    public ClassWithMoreAutowiredConstructor(int a) {
    }
}
