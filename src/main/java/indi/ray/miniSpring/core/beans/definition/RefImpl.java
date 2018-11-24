package indi.ray.miniSpring.core.beans.definition;

import indi.ray.miniSpring.core.utils.StringUtils;

public class RefImpl implements Ref {
    private boolean  required;
    private Class<?> requiredType;
    private String   requiredName;

    public RefImpl(Class<?> clazz, String name, boolean required) {
        this.required = required;
        this.requiredType = clazz;
        this.requiredName = name;
    }

    @Override
    public String toString() {
        return "RefImpl{" +
                "required=" + required +
                ", requiredType=" + requiredType +
                ", requiredName='" + requiredName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Ref)) return false;
        RefImpl that = (RefImpl) obj;
        if (this.required != that.required) return false;
        if (this.requiredType != that.requiredType) return false;
        if (!StringUtils.equals(this.requiredName, that.requiredName)) return false;
        return true;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }

    public void setRequiredType(Class<?> requiredType) {
        this.requiredType = requiredType;
    }

    public String getRequiredName() {
        return requiredName;
    }

    public void setRequiredName(String requiredName) {
        this.requiredName = requiredName;
    }
}
