package com.ray.miniSpring.core.functionTest.annotations.constructorInjection.selfCirCular;

import com.ray.miniSpring.core.annotations.ComponentScan;
import com.ray.miniSpring.core.beans.exception.BeanCurrentlyInCreationException;
import com.ray.miniSpring.core.testBase.BaseConfigurableTester;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.utils.Executable;
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
