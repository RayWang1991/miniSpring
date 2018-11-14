package indi.ray.annotationSpring.core.context.populator;

import indi.ray.annotationSpring.core.beans.definition.BeanDefinition;
import indi.ray.annotationSpring.core.beans.definition.BeanField;
import indi.ray.annotationSpring.core.beans.definition.DependOnRelation;
import indi.ray.annotationSpring.core.beans.exception.BeanNotFoundException;
import indi.ray.annotationSpring.core.beans.factory.BeanFactory;
import indi.ray.annotationSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class DefaultBeanFiller implements BeanFiller {
    private static final Logger logger = Logger.getLogger(DefaultBeanFiller.class);

    public void populateBean(Object bean, BeanDefinition beanDefinition, BeanFactory beanFactory) {
        BeanField[] beanFields = beanDefinition.getFields();
        if (beanFields == null || beanFields.length == 0) {
            return;
        }
        for (BeanField beanField : beanFields) {
            Field field = beanField.getField();
            DependOnRelation dependOnRelation = beanField.getDependOnRelation();
            injectField(bean, field, dependOnRelation, beanFactory);
        }
    }

    private void injectField(Object instance, Field field, DependOnRelation relation,
                             BeanFactory beanFactory) {
        String requiredName = relation.getRequiredName();
        Class<?> requiredClass = relation.getRequiredType();
        Object bean = null;
        if (StringUtils.isNotBlank(requiredName)) {
            try {
                bean = beanFactory.getBean(requiredName);
            } catch (BeanNotFoundException e) {
                logger.error("Bean not found for name: " + requiredName + " continue to find with class");
            }
        }
        if (bean == null) {
            try {
                bean = beanFactory.getBean(requiredClass);
            } catch (BeanNotFoundException e) {
                logger.error("Bean not found for class: " + requiredClass);
            }
        }
        if (bean == null && relation.isRequired()) {
            throw buildNotFoundException(instance, field, requiredName, requiredClass.getName());
        }
        if (bean == null) {
            // bean is not required
            return;
        }
        if (!field.isAccessible()) {
            try {
                field.setAccessible(true);
            } catch (Exception e) {
                logger.error("无法设置属性" + e);
            }
        }
        try {
            field.set(instance, bean);
        } catch (Exception e) {
            logger.error("无法设置属性" + e);
        }
    }

    private BeanNotFoundException buildNotFoundException(Object bean, Field field, String requiredName,
                                                         String requiredType) {
        StringBuilder sb = new StringBuilder();
        sb.append(bean).append("填充").append(field).append("属性时，无法找到");
        if (StringUtils.isNotBlank(requiredName)) {
            sb.append("名为").append(requiredName);
        }
        sb.append("类型为").append(requiredType).append("的Bean");
        return new BeanNotFoundException(sb.toString());
    }
}
