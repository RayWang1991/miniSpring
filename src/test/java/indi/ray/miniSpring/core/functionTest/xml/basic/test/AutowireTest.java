package indi.ray.miniSpring.core.functionTest.xml.basic.test;

import indi.ray.miniSpring.core.context.XmlApplicationContext;
import indi.ray.miniSpring.core.functionTest.xml.basic.PoJoTestBean;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

public class AutowireTest {
    @Test
    public void testAutowiredByName() {
        XmlApplicationContext xmlApplicationContext = new XmlApplicationContext("classPath:xmlTest/loadTestPropertyAutowiredByName.xml");
        PoJoTestBean pojo = xmlApplicationContext.getBean("pojo", PoJoTestBean.class);
        Object obj = xmlApplicationContext.getBean("obj", Object.class);
        AssertUtils.assertTrue(pojo.getObj() == obj, "");
        AssertUtils.assertTrue(pojo.getA() == 10, "");
    }

    @Test
    public void testAutowiredByType() {
        XmlApplicationContext xmlApplicationContext = new XmlApplicationContext("classPath:xmlTest/loadTestPropertyAutowiredByName.xml");
        PoJoTestBean pojo = xmlApplicationContext.getBean("pojo1", PoJoTestBean.class);
        Object object = xmlApplicationContext.getBean("object", Object.class);
        AssertUtils.assertTrue(pojo.getObj() == object, "");
        AssertUtils.assertTrue(pojo.getA() == 15, "");
    }

    @Test
    public void testAutowiredByNameBeans() {
        XmlApplicationContext xmlApplicationContext = new XmlApplicationContext("classPath:xmlTest/loadTestPropertyAutowiredByNameBeans.xml");
        PoJoTestBean pojo = xmlApplicationContext.getBean("pojo", PoJoTestBean.class);
        Object obj = xmlApplicationContext.getBean("obj", Object.class);
        AssertUtils.assertTrue(pojo.getObj() == obj, "");
        AssertUtils.assertTrue(pojo.getA() == 10, "");
    }

    @Test
    public void testAutowiredByTypeBeans() {
        XmlApplicationContext xmlApplicationContext = new XmlApplicationContext("classPath:xmlTest/loadTestPropertyAutowiredByTypeBeans.xml");
        PoJoTestBean pojo = xmlApplicationContext.getBean("pojo1", PoJoTestBean.class);
        Object object = xmlApplicationContext.getBean("object", Object.class);
        AssertUtils.assertTrue(pojo.getObj() == object, "");
        AssertUtils.assertTrue(pojo.getA() == 15, "");
    }
}
