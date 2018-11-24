package indi.ray.miniSpring.core.beans.definition;

import indi.ray.miniSpring.core.beans.exception.BeansException;
import org.w3c.dom.Element;

import java.lang.reflect.Field;

import static indi.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider.ATTRIBUTE_NAME;

public class BeanPropertyResolver {
    private StaticValueRefResolver staticValueRefResolver;

    public BeanPropertyResolver(StaticValueRefResolver staticValueRefResolver) {
        this.staticValueRefResolver = staticValueRefResolver;
    }

    public BeanProperty resolveBeanField(Element element, Class<?> beanClass) {
        String name = element.getAttribute(ATTRIBUTE_NAME);
        ValueOrRef valueOrRef = this.staticValueRefResolver.resolveRefValueForProperty(element);
        BeanProperty bf = buildBeanField(beanClass, name, valueOrRef);
        return bf;
    }

    private BeanProperty buildBeanField(Class<?> beanClass, String fieldName, ValueOrRef valueOrRef) {
        Field field;
        try {
            field = beanClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new BeansException(e.toString());
        }
        return buildBeanField(field, valueOrRef);
    }

    private BeanProperty buildBeanField(Field field, ValueOrRef valueOrRef) {
        BeanProperty beanProperty = new BeanProperty();
        beanProperty.setField(field);
        beanProperty.setFieldName(field.getName());
        beanProperty.setFieldValue(valueOrRef);
        return beanProperty;
    }
}
