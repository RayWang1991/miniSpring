package indi.ray.annotationSpring.core.beans.definition;

import java.lang.reflect.Constructor;

/**
 * This interface defines wrapper for bean's constructor
 */
public interface BeanConstructor {
    /**
     * return the constructor for the bean
     */
    Constructor<?> getConstructor();

    /**
     * return the depend-on relations of the args for the constructor
     */
    DependOnRelation[] getDependOnRelations();
}
