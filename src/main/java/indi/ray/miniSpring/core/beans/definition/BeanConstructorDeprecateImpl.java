package indi.ray.miniSpring.core.beans.definition;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class BeanConstructorDeprecateImpl implements BeanConstructorDeprecate {
    private Constructor<?> constructor;
    private Ref[]          dependOnRelations;

    public BeanConstructorDeprecateImpl(Constructor<?> ct, Ref[] relations) {
        this.constructor = ct;
        this.dependOnRelations = relations;
    }

    @Override
    public String toString() {
        return "BeanConstructorDeprecateImpl{" +
                "constructor=" + constructor +
                ", dependOnRelations=" + Arrays.toString(dependOnRelations) +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof BeanConstructorDeprecateImpl)) return false;
        BeanConstructorDeprecateImpl that = (BeanConstructorDeprecateImpl) obj;
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

    public Ref[] getDependOnRelations() {
        return dependOnRelations;
    }

    public void setDependOnRelations(Ref[] dependOnRelations) {
        this.dependOnRelations = dependOnRelations;
    }
}
