package labTest.loadFile;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;

@Component
public class ClassWithMoreAutowiredConstructor {
    @Autowired
    public ClassWithMoreAutowiredConstructor(String a) {
    }

    @Autowired
    public ClassWithMoreAutowiredConstructor(int a) {
    }
}
