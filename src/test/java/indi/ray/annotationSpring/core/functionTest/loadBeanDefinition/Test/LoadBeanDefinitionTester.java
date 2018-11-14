package indi.ray.annotationSpring.core.functionTest.loadBeanDefinition.Test;

import indi.ray.annotationSpring.core.functionTest.loadBeanDefinition.AnotherBeanClass;
import indi.ray.annotationSpring.core.functionTest.loadBeanDefinition.BeanClass;
import indi.ray.annotationSpring.core.beans.definition.AnnotationBeanDefinition;
import indi.ray.annotationSpring.core.beans.definition.BeanConstructor;
import indi.ray.annotationSpring.core.beans.definition.BeanConstructorImpl;
import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.definition.BeanField;
import indi.ray.annotationSpring.core.beans.definition.BeanFieldImpl;
import indi.ray.annotationSpring.core.beans.definition.DependOnRelation;
import indi.ray.annotationSpring.core.beans.definition.DependOnRelationImpl;
import indi.ray.annotationSpring.core.beans.definition.ScopeEnum;
import indi.ray.annotationSpring.core.context.scanner.ClassPathBeanDefinitionsProvider;
import indi.ray.annotationSpring.core.testBase.BaseTester;
import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.ObjectUtils;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

public class LoadBeanDefinitionTester extends BaseTester {
    @Test
    public void testGenBeanDef() {
        ClassPathBeanDefinitionsProvider provider = new ClassPathBeanDefinitionsProvider();
        BeanDefinition got = provider.generateBeanDefinition(BeanClass.class);
        BeanDefinition expected = new AnnotationBeanDefinition();
        ((AnnotationBeanDefinition) expected).setBeanName("beanClass");
        ((AnnotationBeanDefinition) expected).setBeanClass(BeanClass.class);
        expected.setPrimary(true);
        ((AnnotationBeanDefinition) expected).setScopeEnum(ScopeEnum.SINGLETON);
        try {

            Constructor constructor = BeanClass.class.getConstructor(AnotherBeanClass.class);
            DependOnRelation ctDependOn = new DependOnRelationImpl(AnotherBeanClass.class, "anotherBeanClass", true);
            BeanConstructor beanConstructor = new BeanConstructorImpl(constructor, new DependOnRelation[]{ctDependOn});
            expected.setConstructor(beanConstructor);

            BeanField[] beanFields = new BeanField[1];
            Field field = BeanClass.class.getDeclaredField("bean");
            BeanFieldImpl beanField = new BeanFieldImpl(field, field.getType(), field.getName(), false);
            beanFields[0] = beanField;
            expected.setFields(beanFields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AssertUtils.assertTrue(ObjectUtils.equals(got, expected), "bean 定义应当与预期一致" + "\nexpect:" + expected
                + "\ngot:" + got);
    }

    @Test
    public void testGetMultipleBeanDef() {
        ClassPathBeanDefinitionsProvider provider = new ClassPathBeanDefinitionsProvider();
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents("indi/ray/annotationSpring/core/functionTest/loadBeanDefinition/Package1",
                "indi/ray/annotationSpring/core/functionTest/loadBeanDefinition/Package2");
        AssertUtils.assertTrue(beanDefinitions.size() == 2, "应当产生2个bean definition");
    }
}
