package com.ray.miniSpring.aop.pointCut;

import com.ray.miniSpring.core.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NamedMatchMethodPointCut extends StaticMethodMatcherPointCut {
    List<String> mappedNames = new ArrayList<String>();

    public void setMappedNames(String... names) {
        this.mappedNames = new ArrayList<String>();
        if (names != null && names.length > 0) {
            this.mappedNames.addAll(Arrays.asList(names));
        }
    }

    public NamedMatchMethodPointCut addMappedName(String mappedName) {
        this.mappedNames.add(mappedName);
        return this;
    }

    @Override
    public boolean matches(Method method) {
        for (String mappedName : this.mappedNames) {
            if (StringUtils.equals(mappedName, method.getName())) {
                return true;
            }
        }
        return false;
    }
}
