package indi.ray.miniSpring.aop.functionTest.proxyFactoryBean;

import com.google.common.collect.Lists;
import indi.ray.miniSpring.aop.People;
import indi.ray.miniSpring.aop.Student;
import indi.ray.miniSpring.aop.Studier;
import indi.ray.miniSpring.aop.advise.MethodInterceptor;
import indi.ray.miniSpring.aop.functionTest.proxySetUp.MethodLoggerInterceptor;
import indi.ray.miniSpring.core.context.XmlApplicationContext;
import indi.ray.miniSpring.core.utils.AssertUtils;
import indi.ray.miniSpring.core.utils.StringUtils;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactoryBeanTest {

    // test interceptorNames func
    @Test
    public void testSingleInterceptorName() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:proxyFactoryBeanTest/proxyFactoryBeanBasic.xml");
        People people = applicationContext.getBean("student1", People.class);
        people.say();
        people.getAge();
        people.getName();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMultipleInterceptorName() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:proxyFactoryBeanTest/proxyFactoryBeanBasic.xml");
        List<String> recorder = applicationContext.getBean("nameRecorder", List.class);
        People people = applicationContext.getBean("student2", People.class);
        people.say();
        people.getAge();
        people.getName();

        List<String> want = Lists.newArrayList(new String[]{"say", "saysay", "getAge", "getAgegetAge", "getName", "getNamegetName"});
        AssertUtils.assertEquals(recorder, want, "");
    }

    @Test
    public void testSetAutoDetectInterfaces() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:proxyFactoryBeanTest/proxyFactoryBeanBasic.xml");
        Object student = applicationContext.getBean("student2");
        People people = (People) student;
        people.say();
        people.getAge();
        people.getName();

        Studier studier = (Studier) student;
        studier.study();

        List<String> recorder = applicationContext.getBean("nameRecorder", List.class);
        List<String> want = Lists.newArrayList(new String[]{"say", "saysay", "getAge", "getAgegetAge", "getName", "getNamegetName", "study", "studystudy"});
        AssertUtils.assertEquals(recorder, want, "");
    }

    @Test
    public void testSingleton() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:proxyFactoryBeanTest/proxyFactoryBeanBasic.xml");
        Object student1 = applicationContext.getBean("student1");
        Object student2 = applicationContext.getBean("student1");
        AssertUtils.assertTrue(student1 == student2, "proxies for stuednt should be the same");
    }

    @Test
    public void testPrototypeProxy() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:proxyFactoryBeanTest/proxyFactoryBeanBasic.xml");

        Object student1 = applicationContext.getBean("student3");
        Object student2 = applicationContext.getBean("student3");

        People people1 = (People) student1;
        People people2 = (People) student2;
        people1.say();
        people2.getAge();
        people2.getName();

        AssertUtils.assertTrue(student1 != student2, "proxies for student should not be the same");
        List<String> recorder = applicationContext.getBean("nameRecorder", List.class);
        List<String> want = Lists.newArrayList(new String[]{"say", "saysay", "getAge", "getAgegetAge", "getName", "getNamegetName"});
        AssertUtils.assertEquals(recorder, want, "");
    }
}
