package com.ray.miniSpring.core.functionTest.xml.postProcessors.test;

import com.ray.miniSpring.core.context.XmlApplicationContext;
import com.ray.miniSpring.core.functionTest.xml.postProcessors.Counter;
import com.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

public class BeanPostProcessorTest {
    @Test
    public void testSinglePostProcessor() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:xmlTest/beanPostProcessorTest1.xml");
    }

    @Test
    public void testMultiplePostProcessor() {
        XmlApplicationContext applicationContext = new XmlApplicationContext("classPath:xmlTest/beanPostProcessorTest2.xml");
        Counter counter = applicationContext.getBean("counter", Counter.class);
        AssertUtils.assertTrue(counter.getCount() == 30, "counter is expected to be 30, got " + counter.getCount());
    }
}
