package com.ray.miniSpring.core.context.populator;

import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.beans.definition.AutowireType;
import com.ray.miniSpring.core.beans.definition.BeanProperty;
import com.ray.miniSpring.core.beans.definition.Ref;
import com.ray.miniSpring.core.beans.definition.RefImpl;
import com.ray.miniSpring.core.beans.definition.Value;
import com.ray.miniSpring.core.beans.definition.ValueOrRef;
import com.ray.miniSpring.core.beans.exception.BeanNotFoundException;
import com.ray.miniSpring.core.beans.factory.BeanFactory;
import com.ray.miniSpring.core.utils.BeanUtils;
import com.ray.miniSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ray.miniSpring.core.beans.utils.ValueResolveUtils.getValueObj;

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
                setFieldValue(bean, field, valueOrRef, beanFactory);
            } else {
                setFieldRef(bean, field, valueOrRef, beanFactory);
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
            setFieldRef(bean, field, new RefImpl(fieldType, byName ? fieldName : null, false), beanFactory);
        }
    }


    private void setFieldValue(Object instance, Field field, Value value, BeanFactory beanFactory) {
        Object property = getValueObj(value, field.getType(), beanFactory);
        setProperty(instance, field, property);
    }

    private void setFieldRef(Object instance, Field field, Ref relation,
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

        setProperty(instance, field, bean);
    }


    private void setProperty(Object instance, Field field, Object property) {
        Method writer = getWriteMethod(field, instance.getClass());
        if (writer != null) {
            setPropertyUsingWriter(writer, instance, property);
        } else {
            setPropertyUsingFieldInject(instance, field, property);
        }
    }

    //todo 待优化
    private Method getWriteMethod(Field field, Class<?> clazz) {
        Method writer = null;
        try {
            String readerName = BeanUtils.genReaderName(field);
            String writerName = BeanUtils.genWriterName(field);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz, readerName, writerName);
            writer = pd.getWriteMethod();
        } catch (IntrospectionException e) {
            logger.info(e);
        }
        if (writer != null && !writer.isAccessible()) {
            writer.setAccessible(true);
        }
        return writer;
    }


    private void setPropertyUsingFieldInject(Object instance, Field field, Object property) {
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


    private void setPropertyUsingWriter(Method writer, Object instance, Object property) {
        try {
            writer.invoke(instance, property);
        } catch (Exception e) {
            logger.error(e);
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
