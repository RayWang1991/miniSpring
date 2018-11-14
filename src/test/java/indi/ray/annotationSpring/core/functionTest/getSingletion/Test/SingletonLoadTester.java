package indi.ray.annotationSpring.core.functionTest.getSingletion.Test;

import indi.ray.annotationSpring.core.functionTest.getSingletion.Bean;
import indi.ray.annotationSpring.core.testBase.BaseConfigurableTester;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import org.junit.Test;

public class SingletonLoadTester extends BaseConfigurableTester {
    @Test
    public void testBaseComponentScanWithName() {
        Bean bean = (Bean) applicationContext.getBean("bean");
        bean.serve();
        System.out.println(bean);
    }

    @Test
    public void testBaseComponentScanWithClass() {
        Bean bean = applicationContext.getBean(Bean.class);
        bean.serve();
    }

    @Test
    public void testBaseComponentScanWithNameAndClass() {
        Bean bean = applicationContext.getBean("bean", Bean.class);
        bean.serve();
    }

    @Test
    public void testMultipleLoadSameBean() {
        Bean bean = applicationContext.getBean(Bean.class);
        Bean bean1 = applicationContext.getBean("bean", Bean.class);
        Bean bean2 = (Bean) applicationContext.getBean("bean");
        Bean bean3 = (Bean) applicationContext.getBean("bean");
        AssertUtils.assertTrue(bean == bean1 && bean == bean2 && bean == bean3, "Beans should be the same");
    }
}
