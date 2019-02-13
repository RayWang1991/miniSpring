package com.ray.miniSpring.core.functionTest.annotations.fieldInjection;

import com.ray.miniSpring.core.annotations.ComponentScan;
import com.ray.miniSpring.core.testBase.BaseConfigurableTester;
import com.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

@ComponentScan(basePackageClasses = {FieldInjectionTester.class})
public class FieldInjectionTester extends BaseConfigurableTester {

    @Test
    public void testFieldInjection() {
        Dinner dinner = this.applicationContext.getBean(Dinner.class);
        Apple apple = this.applicationContext.getBean(Apple.class);
        Banana banana = this.applicationContext.getBean("banana", Banana.class);
        Cookie cookie = this.applicationContext.getBean("cookie", Cookie.class);
        AssertUtils.assertTrue(dinner.getApple() == apple, "晚餐吃平安果");
        AssertUtils.assertTrue(dinner.getBanana() == banana, "晚餐吃香蕉");
        AssertUtils.assertTrue(dinner.getCookie1() == cookie, "晚餐吃甜点");
        AssertUtils.assertTrue(dinner.getCookie2() == null, "cookie2 should be null");
        AssertUtils.assertTrue(dinner.getOrange() == null, "orange should be null");
    }

    @Test
    public void testCircular() {
        A a = this.applicationContext.getBean(A.class);
        B b = this.applicationContext.getBean(B.class);
        C c = this.applicationContext.getBean(C.class);
        SelfDependentBean bean = this.applicationContext.getBean(SelfDependentBean.class);

        AssertUtils.assertTrue(a.b == b, "a.b should be b");
        AssertUtils.assertTrue(b.c == c, "b.c should be c");
        AssertUtils.assertTrue(c.a == a, "c.a should be a");
        AssertUtils.assertTrue(bean.getSelfDependentBean() == bean, "bean.selfDependentBean should be itself");
    }
}
