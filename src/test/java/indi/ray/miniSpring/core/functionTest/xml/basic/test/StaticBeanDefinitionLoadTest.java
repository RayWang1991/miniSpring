package indi.ray.miniSpring.core.functionTest.xml.basic.test;

import indi.ray.miniSpring.core.beans.definition.BeanConstructor;
import indi.ray.miniSpring.core.beans.definition.BeanDefinition;
import indi.ray.miniSpring.core.beans.definition.BeanProperty;
import indi.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

import java.util.List;

public class StaticBeanDefinitionLoadTest {
    @Test
    public void testLoadPoJoClassPath() {
        XmlBeanDefinitionProvider beanDefinitionProvider = new XmlBeanDefinitionProvider();
        List<BeanDefinition> bds = beanDefinitionProvider.findBeanDefinitions("classPath:xmlTest/loadTest1.xml");
        AssertUtils.assertTrue(bds.size() == 4, "");
    }

    @Test
    public void testLoadPoJoFilePath() {
        XmlBeanDefinitionProvider beanDefinitionProvider = new XmlBeanDefinitionProvider();
        List<BeanDefinition> bds = beanDefinitionProvider.findBeanDefinitions("/Users/raywang/IdeaProjects/miniSpring/src/test/resources/xmlTest/loadTest1.xml");
        AssertUtils.assertTrue(bds.size() == 4, "");
    }

    @Test
    public void testLoadConstructor() {
        XmlBeanDefinitionProvider beanDefinitionProvider = new XmlBeanDefinitionProvider();
        List<BeanDefinition> bds = beanDefinitionProvider.findBeanDefinitions("classPath:xmlTest/loadTest2.xml");
        AssertUtils.assertTrue(bds.size() == 2, "");
        BeanDefinition bd = bds.get(0);
        BeanDefinition bd1 = bds.get(1);
        BeanConstructor beanConstructor = bd.getBeanConstructor();
        BeanConstructor beanConstructor1 = bd1.getBeanConstructor();
        AssertUtils.assertTrue(beanConstructor.getConstructor() != null, "");
        AssertUtils.assertTrue(beanConstructor1.getConstructor() == null, "");
    }

    @Test
    public void testLoadProperties() {
        XmlBeanDefinitionProvider beanDefinitionProvider = new XmlBeanDefinitionProvider();
        List<BeanDefinition> bds = beanDefinitionProvider.findBeanDefinitions("/Users/raywang/IdeaProjects/miniSpring/src/test/resources/xmlTest/loadTestProperty.xml");
        AssertUtils.assertTrue(bds.size() == 2, "");
        BeanDefinition bd = bds.get(1);
        List<BeanProperty> properties = bd.getBeanProperties();
        AssertUtils.assertTrue(properties.size() == 2, "");
    }
}
