package indi.ray.miniSpring.core.beans.definition;

public enum AutowireType {
    No("no"),

    ByName("byName"),

    ByType("byType");


    private String code;

    AutowireType(String code) {
        this.code = code;
    }

    public static AutowireType getAutowireTypeByCode(String code) {
        for (AutowireType autowireType : values()) {
            if (autowireType.code.equals(code)) {
                return autowireType;
            }
        }
        return null;
    }
}
