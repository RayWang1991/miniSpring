package labTest.reflective.test;

import indi.ray.miniSpring.aop.utils.ReflectiveUtils;
import indi.ray.miniSpring.core.utils.ArrayUtils;
import indi.ray.miniSpring.core.utils.AssertUtils;
import labTest.reflective.Apple;
import labTest.reflective.Student;
import labTest.reflective.Talker;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Set;

public class ReflectiveTest {
    @Test
    public void test1() throws Throwable {
        Apple apple = new Apple();
        Method method = ReflectiveUtils.getMethodRecursively(apple.getClass(), "setName", String.class);
        method.invoke(apple, "apple");
        System.out.println(method.getDeclaringClass());
        System.out.println(method.getDeclaringClass().isInterface());
    }

    @Test
    public void test2() throws Throwable {
        Class<?>[] interfaces = Talker.class.getInterfaces();
        System.out.println(ArrayUtils.genStringForObjArray(interfaces));

        Set<Class<?>> interfaceSet = ReflectiveUtils.getAllInterfacesForClassAsSet(Student.class);
        AssertUtils.assertTrue(interfaceSet.size() == 5, "Talker, Speaker, Dreamer, People, Animal");
        System.out.println(interfaceSet);
    }
}
