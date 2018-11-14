package indi.ray.annotationSpring.core.functionTest.constructorInjection.baseConstructorInjectin;

import indi.ray.annotationSpring.core.annotations.ComponentScan;
import indi.ray.annotationSpring.core.testBase.BaseConfigurableTester;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import org.junit.Test;

@ComponentScan(basePackageClasses = {ConstructorInjectionTester.class})
public class ConstructorInjectionTester extends BaseConfigurableTester {

    @Test
    public void testFieldInjectionNormal() {
        Dinner dinner = this.applicationContext.getBean(Dinner.class);
        Apple apple = this.applicationContext.getBean(Apple.class);
        Banana banana = this.applicationContext.getBean("banana", Banana.class);
        Cookie cookie = this.applicationContext.getBean("cookie", Cookie.class);
        AssertUtils.assertTrue(dinner.getApple() == apple, "晚餐吃平安果");
        AssertUtils.assertTrue(dinner.getBanana() == banana, "晚餐吃香蕉");
        AssertUtils.assertTrue(dinner.getCookie1() == cookie, "晚餐吃甜点");
        AssertUtils.assertTrue(dinner.getCookie2() == null, "cookie2 should be null");
    }

}
