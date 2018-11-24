package indi.ray.miniSpring.core.beans.definition;

import indi.ray.miniSpring.core.beans.exception.BeansException;
import indi.ray.miniSpring.core.beans.utils.TypeUtils;
import indi.ray.miniSpring.core.utils.ObjectUtils;

public class DefaultValueOrRef implements ValueOrRef {
    protected boolean  isValue;
    /**
     * 复用
     * 1. 表value时，表示值的类型
     * a. plain, 表示自身类型
     * b. set/list, 表示元素类型
     * 2. 表ref时，表示引用对象的指定类型
     */
    protected Class<?> type;
    protected String   typeName;
    protected String   raw;
    protected Object   value;
    protected String   refName;
    protected boolean  isRequired   = true;
    protected int      valueSubType = -1;


    @Override
    public String toString() {
        return "DefaultValueOrRef{" +
                "isValue=" + isValue +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", raw='" + raw + '\'' +
                ", value=" + value +
                ", refName='" + refName + '\'' +
                ", isRequired=" + isRequired +
                ", valueSubType=" + valueSubType +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DefaultValueOrRef)) return false;
        DefaultValueOrRef that = (DefaultValueOrRef) obj;
        if (!ObjectUtils.equals(this.value, that.value)) return false;
        if (!ObjectUtils.equals(this.refName, that.refName)) return false;
        if (this.isValue != that.isValue) return false;
        if (this.valueSubType != that.valueSubType) return false;
        if (this.type != that.type) return false;
        if (this.isRequired != that.isRequired) return false;
        return true;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getRawValue() {
        return raw;
    }

    public void setRawValue(String raw) {
        this.raw = raw;
    }

    public void setTypeName(String typeName) {
        this.type = TypeUtils.typeForName(typeName);
    }

    public void setValueSubType(int valueSubType) {
        this.valueSubType = valueSubType;
    }

    public Class<?> getRequiredType() {
        return type;
    }

    public String getRequiredName() {
        return refName;
    }

    public Class<?> getTypeForValue() {
        return type;
    }

    public int getValueSubType() {
        return valueSubType;
    }

    public boolean isValue() {
        return isValue;
    }

    public void setValue(boolean value) {
        isValue = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    //todo factory method
    public static DefaultValueOrRef plainValue(Class<?> type, String rawValue) {
        DefaultValueOrRef defaultValueOrRef = new DefaultValueOrRef();
        defaultValueOrRef.valueSubType = Value.PLAIN_VALUE;
        defaultValueOrRef.isValue = true;
        defaultValueOrRef.raw = rawValue;
        defaultValueOrRef.type = type;
        return defaultValueOrRef;
    }

    public static DefaultValueOrRef nullValue() {
        DefaultValueOrRef defaultValueOrRef = new DefaultValueOrRef();
        defaultValueOrRef.valueSubType = Value.NULL_VALUE;
        defaultValueOrRef.isValue = true;
        return defaultValueOrRef;
    }

    public static DefaultValueOrRef ref(Class<?> type, String refName, boolean isRequired) {
        DefaultValueOrRef defaultValueOrRef = new DefaultValueOrRef();
        defaultValueOrRef.isValue = false;
        defaultValueOrRef.type = type;
        defaultValueOrRef.refName = refName;
        return defaultValueOrRef;
    }

    public static ListSetValueRef listValue() {
        ListSetValueRef listSetValueRef = new ListSetValueRef();
        listSetValueRef.valueSubType = Value.LIST_VALUE;
        listSetValueRef.isValue = true;
        return listSetValueRef;
    }

    public static ListSetValueRef setValue() {
        ListSetValueRef listSetValueRef = new ListSetValueRef();
        listSetValueRef.valueSubType = Value.SET_VALUE;
        listSetValueRef.isValue = true;
        return listSetValueRef;
    }
}
