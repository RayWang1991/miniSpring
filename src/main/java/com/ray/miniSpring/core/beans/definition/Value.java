package com.ray.miniSpring.core.beans.definition;

public interface Value {

    int NULL_VALUE  = 0;
    int PLAIN_VALUE = 1;
    int LIST_VALUE  = 2;
    int SET_VALUE   = 3;
//    int MAP_VALUE   = 4; todo

    int getValueSubType();

    Class<?> getTypeForValue();

    String getRawValue();

    Object getValue();
}
