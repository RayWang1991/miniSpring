package indi.ray.miniSpring.core.functionTest.annotations.loadBeanDefinition.Test;

import indi.ray.miniSpring.core.beans.definition.BeanConstructor;
import indi.ray.miniSpring.core.beans.definition.BeanProperty;
import indi.ray.miniSpring.core.beans.definition.DefaultValueOrRef;
import indi.ray.miniSpring.core.beans.definition.ValueOrRef;
import indi.ray.miniSpring.core.functionTest.annotations.loadBeanDefinition.AnotherBeanClass;
import indi.ray.miniSpring.core.functionTest.annotations.loadBeanDefinition.BeanClass;
import indi.ray.miniSpring.core.beans.definition.AnnotationBeanDefinition;
import indi.ray.miniSpring.core.beans.definition.BeanDefinition;
import indi.ray.miniSpring.core.beans.definition.ScopeEnum;
import indi.ray.miniSpring.core.context.scanner.AnnotationBeanDefinitionsProvider;
import indi.ray.miniSpring.core.testBase.BaseTester;
import indi.ray.miniSpring.core.utils.AssertUtils;
import indi.ray.miniSpring.core.utils.ObjectUtils;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class LoadBeanDefinitionTester extends BaseTester {
    @Test
    public void test() {
        System.out.println(null instanceof BaseTester);
    }

    @Test
    public void testGenBeanDef() {
        AnnotationBeanDefinitionsProvider provider = new AnnotationBeanDefinitionsProvider();
        BeanDefinition got = provider.generateBeanDefinition(BeanClass.class);
        BeanDefinition expected = new AnnotationBeanDefinition();
        ((AnnotationBeanDefinition) expected).setBeanName("beanClass");
        ((AnnotationBeanDefinition) expected).setBeanClass(BeanClass.class);
        expected.setPrimary(true);
        ((AnnotationBeanDefinition) expected).setScopeEnum(ScopeEnum.SINGLETON);
        try {

            Constructor constructor = BeanClass.class.getConstructor(AnotherBeanClass.class);
            ValueOrRef ctDependOn = DefaultValueOrRef.ref(AnotherBeanClass.class, "anotherBeanClass", true);
            BeanConstructor beanConstructor = new BeanConstructor();
            beanConstructor.setConstructor(constructor);
            List<ValueOrRef> consArgs = new ArrayList<ValueOrRef>();
            consArgs.add(ctDependOn);
            beanConstructor.setConstructorArgs(consArgs);
            expected.setConstructor(beanConstructor);

            List<BeanProperty> beanPropertyList = expected.getBeanProperties();
            Field field = BeanClass.class.getDeclaredField("bean");

            BeanProperty beanField = new BeanProperty();
            beanField.setField(field);
            beanField.setFieldName(field.getName());
            beanField.setFieldValue(DefaultValueOrRef.ref(field.getType(), field.getName(), false));
            beanPropertyList.add(beanField);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AssertUtils.assertTrue(ObjectUtils.equals(got, expected), "bean 定义应当与预期一致" + "\nexpect:" + expected
                + "\ngot:" + got);
    }

    @Test
    public void testGetMultipleBeanDef() {
        AnnotationBeanDefinitionsProvider provider = new AnnotationBeanDefinitionsProvider();
        List<BeanDefinition> beanDefinitions = provider.findCandidateComponents("indi/ray/miniSpring/core/functionTest/annotations/loadBeanDefinition/Package1",
                "indi/ray/miniSpring/core/functionTest/annotations/loadBeanDefinition/Package2");
        AssertUtils.assertTrue(beanDefinitions.size() == 2, "应当产生2个bean definition");
    }
}
