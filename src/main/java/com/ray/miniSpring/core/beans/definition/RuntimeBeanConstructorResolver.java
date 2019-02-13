package com.ray.miniSpring.core.beans.definition;

import com.ray.miniSpring.core.beans.factory.BeanFactory;
import org.apache.log4j.Logger;

public class RuntimeBeanConstructorResolver extends AbstractBeanConstructorResolver {
    private static final Logger logger = Logger.getLogger(RuntimeBeanConstructorResolver.class);

    private BeanFactory beanFactory;

    public RuntimeBeanConstructorResolver(StaticValueRefResolver staticValueRefResolver) {
        super(staticValueRefResolver);
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    /**
     * @see AbstractBeanConstructorResolver#resolveConstructor(Class, BeanConstructor)
     */
    public void resolveConstructor(Class<?> clazz, BeanConstructor bc) {
        resolveConstructor(clazz, bc, true);
    }

    @Override
    protected void completeConstructorArgs(BeanConstructor bc) {
        for (ValueOrRef valueOrRef : bc.getConstructorArgs()) {
            if (valueOrRef.isValue()) continue;
            if (valueOrRef.getRequiredType() == null) {
                Class<?> clazz = this.beanFactory.getBeanType(valueOrRef.getRequiredName());
                if (clazz == null) {
                    logger.warn("failed to complete the ref type for name " + valueOrRef.getRequiredName());
                } else {
                    logger.info("complete the ref type " + clazz + " for name " + valueOrRef.getRequiredName());
                    valueOrRef.setType(clazz);
                }
            }
        }
    }
}


