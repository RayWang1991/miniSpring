package labTest.typeSystem;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericArrayTypeTest {
    List<String>[] field;

    @Test
    public void test() throws Exception {
        Field field = this.getClass().getDeclaredField("field");
        GenericArrayType genericArrayType = (GenericArrayType) field.getGenericType();

        // java.lang.reflect.GenericArrayType.getGenericComponentType
        Type componentType =  genericArrayType.getGenericComponentType();
        System.out.println(componentType.getTypeName()); //java.util.List<java.lang.String>
    }
}
