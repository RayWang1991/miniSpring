package indi.ray.annotationSpring.core.beans.definition;

/**
 * This interface wraps the dependency relation between beans,
 * Typically, a bean needs another bean in field properties, constructor parameters...
 */
public interface DependOnRelation {
    /**
     * Return the requiredType for the bean, so further type cast can be performed safely
     */
    Class<?> getRequiredType();

    /**
     * Return the required name for the bean
     */
    String getRequiredName();

    /**
     * This shows whether the bean is 'really' require the other bean, or {@code null} if
     * we cannot find is ok.
     */
    boolean isRequired();
}
