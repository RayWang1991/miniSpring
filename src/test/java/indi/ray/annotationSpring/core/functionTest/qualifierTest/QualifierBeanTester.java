package indi.ray.annotationSpring.core.functionTest.qualifierTest;

import indi.ray.annotationSpring.core.annotations.ComponentScan;
import indi.ray.annotationSpring.core.testBase.BaseConfigurableTester;
import org.junit.Test;

@ComponentScan(basePackages = {"indi/ray/annotationSpring/core/functionTest/qualifierTest"})
public class QualifierBeanTester extends BaseConfigurableTester {
    @Test
    public void testAliasForBean() {
        Apple apple = applicationContext.getBean("pingAn", Apple.class);
        logger.info(apple);
    }

    @Test
    public void testQualifierForBean() {
        Dinner dinner = applicationContext.getBean("dinner", Dinner.class);
        logger.info(dinner);
    }
}
