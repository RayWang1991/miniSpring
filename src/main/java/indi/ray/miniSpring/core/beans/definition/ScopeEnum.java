package indi.ray.miniSpring.core.beans.definition;

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

    public static ScopeEnum getScopeEnumByCode(String code) {
        for (ScopeEnum scopeEnum : values()) {
            if (scopeEnum.code.equals(code)) {
                return scopeEnum;
            }
        }
        return null;
    }
}
