package indi.ray.miniSpring.aop.functionTest.beanNameAutoProxyCreator;

import com.google.common.collect.Lists;
import indi.ray.miniSpring.aop.People;
import indi.ray.miniSpring.core.context.XmlApplicationContext;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

import java.util.List;

public class BeanNameAutoProxyCreatorTest {
    @Test
    public void test() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:proxyFactoryBeanTest/beanNameAutoProxyCreatorTest1.xml");

        Object student = applicationContext.getBean("student");

        People people = (People) student;
        people.say();
        people.getAge();
        people.getName();

        List<String> recorder = applicationContext.getBean("nameRecorder", List.class);
        List<String> want = Lists.newArrayList(new String[]{"say", "saysay", "getAge", "getAgegetAge", "getName", "getNamegetName"});
        AssertUtils.assertEquals(recorder, want, "");
    }
}
