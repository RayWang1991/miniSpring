package labTest.reflective;

import indi.ray.miniSpring.aop.proxy.Advised;
import indi.ray.miniSpring.aop.proxy.AdvisedSupport;
import indi.ray.miniSpring.aop.utils.ReflectiveUtils;
import indi.ray.miniSpring.core.utils.ArrayUtils;
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

        Set<Class<?>> interfaceSet = ReflectiveUtils.getAllInterfacesForClass(Talker.class);
        System.out.println(interfaceSet);
    }
}
