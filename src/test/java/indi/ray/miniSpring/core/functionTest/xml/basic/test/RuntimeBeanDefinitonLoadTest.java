package indi.ray.miniSpring.core.functionTest.xml.basic.test;

import indi.ray.miniSpring.core.context.XmlApplicationContext;
import indi.ray.miniSpring.core.functionTest.xml.basic.ConstructorTestBean;
import indi.ray.miniSpring.core.functionTest.xml.basic.PoJoTestBean;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

public class RuntimeBeanDefinitonLoadTest {
    @Test
    public void testPoJoLoading() {
        XmlApplicationContext xmlApplicationContext = new XmlApplicationContext("classPath:xmlTest/loadTest1.xml");
        PoJoTestBean poJoTestBean = xmlApplicationContext.getBean("pojo1", PoJoTestBean.class);
        AssertUtils.assertNotNull(poJoTestBean,"poJo1 should not be null");
        PoJoTestBean poJoTestBean1 = xmlApplicationContext.getBean("pojo2", PoJoTestBean.class);
        AssertUtils.assertNotNull(poJoTestBean1,"poJo1 should not be null");
        ConstructorTestBean constructorTestBean = xmlApplicationContext.getBean(ConstructorTestBean.class);
        AssertUtils.assertNotNull(constructorTestBean,"bean should not be null");
    }

    @Test
    public void testRuntimeConstructorResolve(){
        XmlApplicationContext xmlApplicationContext = new XmlApplicationContext("classPath:xmlTest/loadTest3.xml");
        ConstructorTestBean constructorTestBean1 = xmlApplicationContext.getBean("constructorTestBean1", ConstructorTestBean.class);
        ConstructorTestBean constructorTestBean2 = xmlApplicationContext.getBean("constructorTestBean2", ConstructorTestBean.class);
        ConstructorTestBean constructorTestBean3 = xmlApplicationContext.getBean("constructorTestBean3", ConstructorTestBean.class);
        ConstructorTestBean constructorTestBean4 = xmlApplicationContext.getBean("constructorTestBean4", ConstructorTestBean.class);
        ConstructorTestBean constructorTestBean5 = xmlApplicationContext.getBean("constructorTestBean5", ConstructorTestBean.class);
        ConstructorTestBean constructorTestBean6 = xmlApplicationContext.getBean("constructorTestBean6", ConstructorTestBean.class);
        ConstructorTestBean constructorTestBean7 = xmlApplicationContext.getBean("constructorTestBean7", ConstructorTestBean.class);

        AssertUtils.assertEquals(constructorTestBean1.consString,"(Object,PoJoTestBean)","");
        AssertUtils.assertEquals(constructorTestBean2.consString,"(Object,PoJoTestBean,int)","");
        AssertUtils.assertEquals(constructorTestBean3.consString,"(Object,String)","");
        AssertUtils.assertEquals(constructorTestBean4.consString,"(Object[],int[])","");
        AssertUtils.assertEquals(constructorTestBean5.consString,"(Integer)","");
        AssertUtils.assertEquals(constructorTestBean6.consString,"(List<Integer>)class java.util.ArrayList","");
        AssertUtils.assertEquals(constructorTestBean7.consString,"(Set<String>)class java.util.HashSet true","");
    }
}
