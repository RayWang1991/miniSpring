package com.ray.miniSpring.core.beans.definition;

import com.ray.miniSpring.core.utils.ObjectUtils;

import java.lang.reflect.Field;

public class BeanProperty {
    private String     fieldName;
    private Field      field;
    private ValueOrRef fieldValue;

    @Override
    public String toString() {
        return "BeanProperty{" +
                "fieldName='" + fieldName + '\'' +
                ", field=" + field +
                ", fieldValue=" + fieldValue +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanProperty that = (BeanProperty) o;
        return ObjectUtils.equals(fieldName, that.fieldName) &&
                ObjectUtils.equals(field, that.field) &&
                ObjectUtils.equals(fieldValue, that.fieldValue);
    }


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public ValueOrRef getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(ValueOrRef fieldValue) {
        this.fieldValue = fieldValue;
    }
}
