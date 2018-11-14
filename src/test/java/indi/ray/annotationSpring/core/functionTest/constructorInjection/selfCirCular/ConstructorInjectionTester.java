package indi.ray.annotationSpring.core.functionTest.constructorInjection.selfCirCular;

import indi.ray.annotationSpring.core.annotations.ComponentScan;
import indi.ray.annotationSpring.core.beans.exception.BeanCurrentlyInCreationException;
import indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin.Apple;
import indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin.Banana;
import indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin.Cookie;
import indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin.Dinner;
import indi.ray.annotationSpring.core.testBase.BaseConfigurableTester;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.Executable;
import org.junit.Test;

@ComponentScan(basePackageClasses = {ConstructorInjectionTester.class})
public class ConstructorInjectionTester extends BaseConfigurableTester {


    @Test
    public void testSelfCircular() {
        AssertUtils.assertThrows(BeanCurrentlyInCreationException.class, new Executable() {
            public void execute() throws Exception {
                SelfDependentBean bean = applicationContext.getBean(SelfDependentBean.class);
            }
        });
    }
}
