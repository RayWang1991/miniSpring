package labTest.typeSystem;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterizedTypeTest <T, R> {
    private List<T>         list;
    private Map<T, R>       map;
    private Map.Entry<T, R> entry;

    @Test
    public void test() throws Exception {

        Field mapField = this.getClass().getDeclaredField("map");

        // java.lang.reflect.Field.getGenericType
        Type typeMap = mapField.getGenericType();
        System.out.println(typeMap.getTypeName()); // java.util.List<T>

        ParameterizedType paramType = (ParameterizedType) typeMap;

        // java.lang.reflect.ParameterizedType.getRawType
        Type rawType = paramType.getRawType();
        System.out.println(rawType.getTypeName()); // java.util.Map

        // java.lang.reflect.ParameterizedType.getActualTypeArguments
        Type[] typeArgs = paramType.getActualTypeArguments();
        for (Type type : typeArgs) {
            System.out.println(type.getTypeName()); // T, R
        }

        // java.lang.reflect.ParameterizedType.getOwnerType
        Type onwerType = paramType.getOwnerType();
        System.out.println(onwerType); // null


        Field entryField = this.getClass().getDeclaredField("entry");
        ParameterizedType entryType = (ParameterizedType) entryField.getGenericType();

        // java.lang.reflect.ParameterizedType.getOwnerType
        System.out.println(entryType.getOwnerType().getTypeName()); // java.util.Map
    }

}
