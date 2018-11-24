package indi.ray.miniSpring.core.annotations;

import indi.ray.miniSpring.core.beans.definition.ScopeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    ScopeEnum value() default ScopeEnum.SINGLETON;
}