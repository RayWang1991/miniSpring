package indi.ray.annotationSpring.core.beans.definition;

public enum ScopeEnum {

    /**
     * singleton type for bean's scope
     */
    SINGLETON("singleton"),

    /**
     * prototype type for bean's scope
     */
    PROTOTYPE("prototype");

    String code;

    ScopeEnum(String code) {
        this.code = code;
    }
}
