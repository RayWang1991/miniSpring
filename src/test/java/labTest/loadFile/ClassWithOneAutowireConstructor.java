package labTest.loadFile;

import indi.ray.annotationSpring.core.annotations.Autowired;
import indi.ray.annotationSpring.core.annotations.Component;

@Component
public class ClassWithOneAutowireConstructor {
    @Autowired
    public ClassWithOneAutowireConstructor(int a){
    }
}
