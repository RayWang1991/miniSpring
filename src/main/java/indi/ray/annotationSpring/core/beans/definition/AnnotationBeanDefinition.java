package indi.ray.annotationSpring.core.beans.definition;

import indi.ray.annotationSpring.core.annotations.Component;
import indi.ray.annotationSpring.core.utils.AssertUtils;

import java.lang.reflect.Constructor;

public class AnnotationBeanDefinition extends AbstractBeanDefinition {


    @Override
    public void validate() {
        super.validate();
        Class<?> clazz = this.getBeanClass();
        AssertUtils.assertTrue(clazz.isAnnotationPresent(Component.class), "必须被@Commponent注解标注");
        validateConstructor();
        validateFields();
    }

    private void validateConstructor() {
        AssertUtils.assertNotNull(this.getConstructor(),"必须存在构造器");
    }

    private void validateFields() {
        BeanField[] beanFields = this.getFields();
        if (beanFields == null || beanFields.length == 0) {
            return;
        }

        for (BeanField beanField : beanFields) {
            DependOnRelation dependOnRelation = beanField.getDependOnRelation();
            AssertUtils.assertNotNull(dependOnRelation.getRequiredType(),"必须存在依赖类型");
            AssertUtils.assertTrue(!dependOnRelation.getRequiredType().isPrimitive(),"依赖类型不可为基础类型");
        }
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
