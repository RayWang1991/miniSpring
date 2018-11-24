package indi.ray.miniSpring.core.beans.definition;

import java.lang.reflect.Constructor;

/**
 * This interface defines wrapper for bean's constructor
 */
public interface BeanConstructorDeprecate {
    /**
     * return the constructor for the bean
     */
    Constructor<?> getConstructor();

    /**
     * return the depend-on relations of the args for the constructor
     */
    Ref[] getDependOnRelations();
}
