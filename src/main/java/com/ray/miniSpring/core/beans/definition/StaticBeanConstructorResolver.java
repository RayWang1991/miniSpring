package com.ray.miniSpring.core.beans.definition;

import org.apache.log4j.Logger;

/**
 * resolve Bean constructor statically, that is, not to resolve the constructor really, just collect information of it.
 * To resolve the constructor in bean creation time (all bean definitions are loaded that time)
 */
public class StaticBeanConstructorResolver extends AbstractBeanConstructorResolver {
    private static final Logger logger = Logger.getLogger(StaticBeanConstructorResolver.class);

    public StaticBeanConstructorResolver(StaticValueRefResolver staticValueRefResolver) {
        super(staticValueRefResolver);
    }

    @Override
    /**
     * @see AbstractBeanConstructorResolver#resolveConstructor(Class, BeanConstructor)
     */
    public void resolveConstructor(Class<?> clazz, BeanConstructor bc) {
        resolveConstructor(clazz, bc, false);
    }

    @Override
    protected void completeConstructorArgs(BeanConstructor bd) {
        throw new UnsupportedOperationException();
    }

}


