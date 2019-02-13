package com.ray.miniSpring.core.beans.definition;

import com.ray.miniSpring.core.utils.ObjectUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanConstructor {
    private boolean                  needRuntimeResolve;
    private Constructor<?>           constructor;
    private Map<Integer, ValueOrRef> argsIndexMap;
    private List<ValueOrRef>         constructorArgs = new ArrayList();

    public BeanConstructor() {
        super();
    }

    @Override
    public String toString() {
        return "BeanConstructor{" +
                "constructor=" + constructor +
                ", argsIndexMap=" + argsIndexMap +
                ", constructorArgs=" + constructorArgs +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof BeanConstructor)) return false;
        BeanConstructor that = (BeanConstructor) obj;
        if (!ObjectUtils.equals(that.constructor, this.constructor)) return false;
        if (!ObjectUtils.equals(that.constructorArgs, this.constructorArgs)) return false;
        return true;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }


    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public List<ValueOrRef> getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(List<ValueOrRef> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public Map<Integer, ValueOrRef> getArgsIndexMap() {
        if (this.argsIndexMap == null) {
            this.argsIndexMap = new HashMap<Integer, ValueOrRef>();
        }
        return argsIndexMap;
    }

    public void setArgsIndexMap(Map<Integer, ValueOrRef> argsIndexMap) {
        this.argsIndexMap = argsIndexMap;
    }

    public boolean isNeedRuntimeResolve() {
        return needRuntimeResolve;
    }

    public void setNeedRuntimeResolve(boolean needRuntimeResolve) {
        this.needRuntimeResolve = needRuntimeResolve;
    }
}
