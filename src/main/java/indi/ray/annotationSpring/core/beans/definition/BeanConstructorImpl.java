package indi.ray.annotationSpring.core.beans.definition;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class BeanConstructorImpl implements BeanConstructor {
    private Constructor<?>     constructor;
    private DependOnRelation[] dependOnRelations;

    public BeanConstructorImpl(Constructor<?> ct, DependOnRelation[] relations) {
        this.constructor = ct;
        this.dependOnRelations = relations;
    }

    @Override
    public String toString() {
        return "BeanConstructorImpl{" +
                "constructor=" + constructor +
                ", dependOnRelations=" + Arrays.toString(dependOnRelations) +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof BeanConstructorImpl)) return false;
        BeanConstructorImpl that = (BeanConstructorImpl) obj;
        if (!this.constructor.equals(that.constructor)) return false;
        if (!Arrays.equals(this.dependOnRelations, that.dependOnRelations)) return false;
        return true;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public DependOnRelation[] getDependOnRelations() {
        return dependOnRelations;
    }

    public void setDependOnRelations(DependOnRelation[] dependOnRelations) {
        this.dependOnRelations = dependOnRelations;
    }
}
