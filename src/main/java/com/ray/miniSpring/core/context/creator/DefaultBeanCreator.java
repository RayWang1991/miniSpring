package com.ray.miniSpring.core.context.creator;

import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.beans.definition.BeanConstructor;
import com.ray.miniSpring.core.beans.definition.RuntimeBeanConstructorResolver;
import com.ray.miniSpring.core.beans.definition.ValueOrRef;
import com.ray.miniSpring.core.beans.exception.BeansException;
import com.ray.miniSpring.core.beans.factory.BeanFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.List;

import static com.ray.miniSpring.core.beans.utils.ValueResolveUtils.getRefObj;
import static com.ray.miniSpring.core.beans.utils.ValueResolveUtils.getValueObj;

public class DefaultBeanCreator implements RuntimeBeanCreator {
    private static final Logger logger = Logger.getLogger(DefaultBeanCreator.class);

    private RuntimeBeanConstructorResolver runtimeBeanConstructorResolver;

    /**
     * @param beanDefinition
     * @param beanFactory
     * @return
     * @see BeanCreator#createBean(BeanDefinition, BeanFactory)
     */
    public Object createBean(BeanDefinition beanDefinition, BeanFactory beanFactory) {

        BeanConstructor beanConstructor = beanDefinition.getConstructor();

        if (beanConstructor.isNeedRuntimeResolve()) {
            doRuntimeResolve(beanDefinition, beanFactory);
        }

        Constructor<?> constructor = beanConstructor.getConstructor();
        AssertUtils.assertNotNull(constructor, "constructor for bean must not be null " +
                beanDefinition.getBeanName());

        // carg for constructor arg
        List<ValueOrRef> valueOrRefs = beanConstructor.getConstructorArgs();
        if (valueOrRefs == null || valueOrRefs.size() == 0) {
            return doCreateBean(constructor);
        }

        Object[] args = new Object[valueOrRefs.size()];
        Class<?> types[] = constructor.getParameterTypes();
        for (int i = 0; i < valueOrRefs.size(); i++) {
            ValueOrRef valueOrRef = valueOrRefs.get(i);
            Object arg = null;
            if (valueOrRef.isValue()) {
                arg = getValueObj(valueOrRef, types[i], beanFactory);
            } else {
                arg = getRefObj(valueOrRef, beanFactory);
            }
            args[i] = arg;
        }

        return doCreateBean(constructor, args);
    }

    protected void doRuntimeResolve(BeanDefinition bd, BeanFactory beanFactory) {
        if (this.runtimeBeanConstructorResolver == null) {
            throw new BeansException("need runtime constructor resolver " + bd);
        }
        this.runtimeBeanConstructorResolver.resolveConstructor(bd.getBeanClass(), bd.getBeanConstructor(), true);
    }



    private Object doCreateBean(Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            // todo, 这里考虑把异常情况统一放在beanFactory处理?
            e.printStackTrace();
            return null;
        }
    }

    public void setRuntimeBeanConstructorResolver(RuntimeBeanConstructorResolver runtimeBeanConstructorResolver) {
        this.runtimeBeanConstructorResolver = runtimeBeanConstructorResolver;
    }
}
