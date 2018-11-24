package indi.ray.miniSpring.core.functionTest.annotations.qualifierTest;

import indi.ray.miniSpring.core.annotations.ComponentScan;
import indi.ray.miniSpring.core.testBase.BaseConfigurableTester;
import org.junit.Test;

@ComponentScan(basePackages = {"indi/ray/miniSpring/core/functionTest/annotations/qualifierTest"})
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
