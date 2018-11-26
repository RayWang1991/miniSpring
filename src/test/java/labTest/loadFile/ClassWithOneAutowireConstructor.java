package labTest.loadFile;

import indi.ray.miniSpring.core.annotations.Autowired;
import indi.ray.miniSpring.core.annotations.Component;

@Component
public class ClassWithOneAutowireConstructor {
    @Autowired
    public ClassWithOneAutowireConstructor(int a){
    }
}
