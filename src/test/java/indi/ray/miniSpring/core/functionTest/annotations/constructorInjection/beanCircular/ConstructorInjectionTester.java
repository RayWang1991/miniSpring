package indi.ray.miniSpring.core.functionTest.annotations.constructorInjection.beanCircular;

import indi.ray.miniSpring.core.annotations.ComponentScan;
import indi.ray.miniSpring.core.testBase.BaseConfigurableTester;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

@ComponentScan(basePackageClasses = {ConstructorInjectionTester.class})
public class ConstructorInjectionTester extends BaseConfigurableTester {


    @Test
    public void testABCCircular() {
        A a = this.applicationContext.getBean(A.class);
        B b = this.applicationContext.getBean(B.class);
        C c = this.applicationContext.getBean(C.class);

        AssertUtils.assertTrue(a.b == b, "a.b should be b");
        AssertUtils.assertTrue(b.c == c, "b.c should be c");
        AssertUtils.assertTrue(c.a == a, "c.a should be a");
    }
}
