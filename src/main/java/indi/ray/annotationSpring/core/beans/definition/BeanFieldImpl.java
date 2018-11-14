package indi.ray.annotationSpring.core.beans.definition;

import indi.ray.annotationSpring.core.utils.AssertUtils;
import indi.ray.annotationSpring.core.utils.ObjectUtils;

import java.lang.reflect.Field;

public class BeanFieldImpl implements BeanField {
    Field field;

    DependOnRelation dependOnRelation;

    public BeanFieldImpl(Field field, Class<?> clazz, String name, boolean required) {
        AssertUtils.assertNotNull(field, "field 不可为空" );
        this.field = field;
        this.dependOnRelation = new DependOnRelationImpl(clazz, name, required);
    }

    @Override
    public String toString() {
        return "BeanFieldImpl{" +
                "field=" + field +
                ", dependOnRelation=" + dependOnRelation +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof BeanFieldImpl)) return false;
        BeanFieldImpl that = (BeanFieldImpl) obj;
        if (!this.field.equals(that.field)) return false;
        if (!ObjectUtils.equals(this.dependOnRelation, that.dependOnRelation)) return false;
        return true;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public DependOnRelation getDependOnRelation() {
        return dependOnRelation;
    }

    public void setDependOnRelation(DependOnRelation dependOnRelation) {
        this.dependOnRelation = dependOnRelation;
    }
}
