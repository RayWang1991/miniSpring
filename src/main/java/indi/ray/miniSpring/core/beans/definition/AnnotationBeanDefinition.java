package indi.ray.miniSpring.core.beans.definition;

import indi.ray.miniSpring.core.annotations.Component;
import indi.ray.miniSpring.core.utils.AssertUtils;

import java.util.List;

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
        List<BeanProperty> beanPropertyList = this.getBeanProperties();
        if (beanPropertyList == null || beanPropertyList.size() == 0) {
            return;
        }

        for (BeanProperty beanProperty : beanPropertyList) {
            Ref dependOnRelation = beanProperty.getFieldValue();
            AssertUtils.assertNotNull(dependOnRelation.getRequiredType(),"必须存在依赖类型");
            AssertUtils.assertTrue(!dependOnRelation.getRequiredType().isPrimitive(),"依赖类型不可为基础类型");
        }
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
