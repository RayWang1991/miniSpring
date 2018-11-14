package indi.ray.annotationSpring.core.beans.definition;

import indi.ray.annotationSpring.core.utils.StringUtils;

public class DependOnRelationImpl implements DependOnRelation {
    private boolean  required;
    private Class<?> requiredType;
    private String   requiredName;

    public DependOnRelationImpl(Class<?> clazz, String name, boolean required) {
        this.required = required;
        this.requiredType = clazz;
        this.requiredName = name;
    }

    @Override
    public String toString() {
        return "DependOnRelationImpl{" +
                "required=" + required +
                ", requiredType=" + requiredType +
                ", requiredName='" + requiredName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof DependOnRelation)) return false;
        DependOnRelationImpl that = (DependOnRelationImpl) obj;
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
