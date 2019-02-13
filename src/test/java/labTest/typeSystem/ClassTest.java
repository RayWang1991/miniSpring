package labTest.typeSystem;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ClassTest {
    private Object obj;

    @Test
    public void test()throws Exception{
        Field field = getClass().getDeclaredField("obj");
        Type type =field.getGenericType();

        System.out.println(type.getTypeName()); //Object
    }
}
