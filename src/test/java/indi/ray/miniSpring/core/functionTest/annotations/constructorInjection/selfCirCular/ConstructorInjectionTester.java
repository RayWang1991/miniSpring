package indi.ray.miniSpring.core.functionTest.annotations.constructorInjection.selfCirCular;

import indi.ray.miniSpring.core.annotations.ComponentScan;
import indi.ray.miniSpring.core.beans.exception.BeanCurrentlyInCreationException;
import indi.ray.miniSpring.core.testBase.BaseConfigurableTester;
import indi.ray.miniSpring.core.utils.AssertUtils;
import indi.ray.miniSpring.core.utils.Executable;
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
