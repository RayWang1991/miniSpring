package labTest.typeSystem;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Map;

public class WildcardTypeTest {

    private Map<? extends Integer, ? super Number> map;

    @Test
    public void test() throws Exception {
        Field field = getClass().getDeclaredField("map");
        Type type = field.getGenericType();
        ParameterizedType parameterizedType = (ParameterizedType) type;

        Type[] typeArgs = parameterizedType.getActualTypeArguments();
        for (Type innerType : typeArgs) {
            if (innerType instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) innerType;

                // java.lang.reflect.WildcardType.getUpperBounds
                Type[] uppers = wildcardType.getUpperBounds();
                for (Type upper : uppers) {
                    System.out.println(upper.getTypeName()); // 1 : Integer; 2 Object
                }

                // java.lang.reflect.WildcardType.getLowerBounds
                Type[] lowers = wildcardType.getLowerBounds();
                for (Type lower : lowers) {
                    System.out.println(lower.getTypeName()); // 1 : [0]; 2 : Number
                }
            }
        }
    }
}
