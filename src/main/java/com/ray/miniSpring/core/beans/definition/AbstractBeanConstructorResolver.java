package com.ray.miniSpring.core.beans.definition;

import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.beans.exception.BeanConstructorNotFoundException;
import com.ray.miniSpring.core.beans.exception.InvalidXmlException;
import com.ray.miniSpring.core.beans.utils.TypeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import java.lang.reflect.Constructor;

import static com.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider.ATTRIBUTE_INDEX;
import static com.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider.ATTRIBUTE_TYPE;

/**
 * resolve Bean constructor statically, that is, not to resolve the constructor really, just collect information of it.
 * To resolve the constructor in bean creation time (all bean definitions are loaded that time)
 */
public abstract class AbstractBeanConstructorResolver {
    private static final Logger logger = Logger.getLogger(AbstractBeanConstructorResolver.class);

    protected StaticValueRefResolver staticValueRefResolver;

    public AbstractBeanConstructorResolver(StaticValueRefResolver staticValueRefResolver) {
        this.staticValueRefResolver = staticValueRefResolver;
    }

    public void addConstructorArg(Element element, BeanConstructor bc) {

        ValueOrRef valueOrRef = staticValueRefResolver.resolveRefValueForConstructorArg(element);


        bc.getConstructorArgs().add(valueOrRef);

        if (element.hasAttribute(ATTRIBUTE_INDEX)) {
            int index = Integer.parseInt(element.getAttribute(ATTRIBUTE_INDEX));
            if (index < 0) {
                throw new InvalidXmlException("constructor arg index should >= 0");
            }
            if (bc.getArgsIndexMap().get(index) != null) {
                throw new InvalidXmlException("constructor arg index:" + index + "should be unique");
            }
            bc.getArgsIndexMap().put(index, valueOrRef);
        }


        if (!element.hasAttribute(ATTRIBUTE_TYPE)) {
            bc.setNeedRuntimeResolve(true);
        } else {
            bc.setNeedRuntimeResolve(false);
            String type = element.getAttribute(ATTRIBUTE_TYPE);
            valueOrRef.setTypeName(type);
        }
    }

    public abstract void resolveConstructor(Class<?> clazz, BeanConstructor bc);

    protected abstract void completeConstructorArgs(BeanConstructor bd);

    protected void precheckConstructor(BeanConstructor bc) {
        // check index
        AssertUtils.assertTrue(bc.getArgsIndexMap().size() == 0 || bc.getArgsIndexMap().size() == bc.getConstructorArgs().size(), "some args are labeled with index, while others not");
        if (bc.getArgsIndexMap().size() > 0) {
            bc.getConstructorArgs().clear();
            for (int i = 0; i < bc.getArgsIndexMap().size(); i++) {
                ValueOrRef valueOrRef = bc.getArgsIndexMap().get(i);
                if (valueOrRef == null) {
                    throw new InvalidXmlException("constructor arg index is missing " + i);
                }
                bc.getConstructorArgs().add(valueOrRef);
            }
        }
    }

    public void resolveConstructor(Class<?> clazz, BeanConstructor bc, boolean inRuntime) {
        if (bc.getConstructor() != null) {
            return;
        }
        if (bc.isNeedRuntimeResolve() && !inRuntime) {
            // constructor needs to be resolved in runtime
            // extend point 1
            return;
        }

        precheckConstructor(bc);

        if (bc.isNeedRuntimeResolve()) {
            if (inRuntime) {
                // extend point 2
                completeConstructorArgs(bc);
            }
        }

        Constructor<?> exposeConstructor = null;

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors == null || constructors.length == 0) {
            throw new BeanConstructorNotFoundException("could not found constructors for " + clazz);
        }

        // find constructor by strategies
        // 1. length
        // 2. type match

        for (Constructor<?> constructor : constructors) {
            Class<?>[] argTypes = constructor.getParameterTypes();
            boolean found = true;
            if (argTypes.length == bc.getConstructorArgs().size()) {
                for (int i = 0; i < argTypes.length; i++) {
                    Class<?> typeToMatch = argTypes[i];
                    ValueOrRef valueOrRef = bc.getConstructorArgs().get(i);
                    if (!TypeUtils.isTypeMatch(typeToMatch, valueOrRef)) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    exposeConstructor = constructor;
                    break;
                }
            }
        }
        if (exposeConstructor == null) {
            throw new BeanConstructorNotFoundException("could not found constructors for " + clazz);
        }
        bc.setConstructor(exposeConstructor);
    }

}


