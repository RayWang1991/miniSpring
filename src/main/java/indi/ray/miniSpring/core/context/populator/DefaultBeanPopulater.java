package indi.ray.miniSpring.core.context.populator;

import indi.ray.miniSpring.core.beans.definition.AutowireType;
import indi.ray.miniSpring.core.beans.definition.BeanDefinition;
import indi.ray.miniSpring.core.beans.definition.BeanProperty;
import indi.ray.miniSpring.core.beans.definition.Ref;
import indi.ray.miniSpring.core.beans.definition.RefImpl;
import indi.ray.miniSpring.core.beans.definition.Value;
import indi.ray.miniSpring.core.beans.definition.ValueOrRef;
import indi.ray.miniSpring.core.beans.exception.BeanNotFoundException;
import indi.ray.miniSpring.core.beans.factory.BeanFactory;
import indi.ray.miniSpring.core.context.creator.BeanCreator;
import indi.ray.miniSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static indi.ray.miniSpring.core.beans.utils.ValueResolveUtils.getValueObj;

public class DefaultBeanPopulater implements BeanPopulater {
    private static final Logger logger = Logger.getLogger(DefaultBeanPopulater.class);

    public void populateBean(Object bean, BeanDefinition beanDefinition, BeanFactory beanFactory) {
        List<BeanProperty> beanPropertyList = beanDefinition.getBeanProperties();
        if (beanPropertyList == null || beanPropertyList.size() == 0) {
            return;
        }

        for (BeanProperty beanProperty : beanPropertyList) {
            Field field = beanProperty.getField();
            ValueOrRef valueOrRef = beanProperty.getFieldValue();
            if (valueOrRef.isValue()) {
                injectFieldValue(bean, field, valueOrRef, beanFactory);
            } else {
                injectFieldRef(bean, field, valueOrRef, beanFactory);
            }
        }

        if (beanDefinition.getAutowireType() == AutowireType.No) {
            return;
        }

        Set<String> populatedNames = new HashSet<String>(beanPropertyList.size());
        for (BeanProperty beanProperty : beanPropertyList) {
            populatedNames.add(beanProperty.getFieldName());
        }
        populateBeanAutowire(bean, populatedNames, beanFactory, beanDefinition.getAutowireType() == AutowireType.ByName);
    }

    public void populateBeanAutowire(Object bean, Set<String> populatedNames, BeanFactory beanFactory, boolean byName) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            if (fieldType.isPrimitive()) continue;
            if (populatedNames.contains(fieldName)) continue;
            injectFieldRef(bean, field, new RefImpl(fieldType, byName ? fieldName : null, false), beanFactory);
        }
    }

    private void injectFieldValue(Object instance, Field field, Value value, BeanFactory beanFactory) {
        Object property = getValueObj(value, field.getType(), beanFactory);
        setPropertyForField(field, instance, property);
    }

    private void injectFieldRef(Object instance, Field field, Ref relation,
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
        setPropertyForField(field, instance, bean);
    }


    private void setPropertyForField(Field field, Object instance, Object property) {
        if (!field.isAccessible()) {
            try {
                field.setAccessible(true);
            } catch (Exception e) {
                logger.error("无法设置属性" + e);
            }
        }
        try {
            field.set(instance, property);
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
