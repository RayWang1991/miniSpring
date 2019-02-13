package com.ray.miniSpring.core.utils;

import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import org.apache.log4j.Logger;

public class BeanDefinitionUtils {
    public static BeanDefinition validateAndReturn(BeanDefinition bd, Logger logger) {
        try {
            bd.validate();
        } catch (IllegalArgumentException e) {
            logger.error(e);
            return null;
        }
        return bd;
    }
}
