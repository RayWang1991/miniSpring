package indi.ray.annotationSpring.core.beans.definition;


import java.lang.reflect.Field;

/**
 * This interface wraps the field of the bean
 */
public interface BeanField {
    /**
     * return the (raw) field of the bean
     */
    Field getField();

    /**
     * return the dependency of the field for the bean
     */
    DependOnRelation getDependOnRelation();
}
