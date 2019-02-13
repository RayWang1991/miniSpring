package labTest.typeSystem;

import com.ray.miniSpring.core.API.Ordered;
import org.junit.Test;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class TypeVariableTest <K extends Number & Ordered, S> {
    private Map<K, S> field;

    @Test
    public void test() throws Exception {
        Field field = this.getClass().getDeclaredField("field");
        Type[] typeArgs = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

        for (Type type : typeArgs) {
            TypeVariable typeVariable = (TypeVariable) type;
            // java.lang.reflect.TypeVariable.getName
            System.out.println(typeVariable.getName()); //  K, S

            // java.lang.reflect.TypeVariable.getBounds
            Type[] bounds = typeVariable.getBounds();
            for (Type bound : bounds) {
                System.out.println(bound.getTypeName()); // K : Number, Ordered; S : Object
            }

            // java.lang.reflect.TypeVariable.getGenericDeclaration
            Object obj = typeVariable.getGenericDeclaration();
            System.out.println(obj); // class TypeVariableTest

            // java.lang.reflect.TypeVariable.getAnnotatedBounds
            AnnotatedType[] annotations = typeVariable.getAnnotatedBounds();
            for (AnnotatedType annotationType : annotations) {
                System.out.println(annotationType.getType().getTypeName());
            }
        }
    }
}
