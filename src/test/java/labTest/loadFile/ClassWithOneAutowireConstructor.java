package labTest.loadFile;

import com.ray.miniSpring.core.annotations.Autowired;
import com.ray.miniSpring.core.annotations.Component;

@Component
public class ClassWithOneAutowireConstructor {
    @Autowired
    public ClassWithOneAutowireConstructor(int a){
    }
}
