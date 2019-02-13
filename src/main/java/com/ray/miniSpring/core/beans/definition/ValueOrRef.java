package com.ray.miniSpring.core.beans.definition;

public interface ValueOrRef extends Ref, Value {

    boolean isValue();

    void setTypeName(String typeName);

    void setType(Class<?> clazz);
}
