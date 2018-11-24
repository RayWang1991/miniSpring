package indi.ray.miniSpring.core.beans.utils;

import indi.ray.miniSpring.core.beans.definition.ListSetValueRef;
import indi.ray.miniSpring.core.beans.definition.Ref;
import indi.ray.miniSpring.core.beans.definition.Value;
import indi.ray.miniSpring.core.beans.definition.ValueOrRef;
import indi.ray.miniSpring.core.beans.exception.BeanNotFoundException;
import indi.ray.miniSpring.core.beans.exception.BeansException;
import indi.ray.miniSpring.core.beans.factory.BeanFactory;
import indi.ray.miniSpring.core.utils.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static indi.ray.miniSpring.core.beans.definition.Value.*;

public class ValueResolveUtils {
    private static final Logger logger = Logger.getLogger(ValueResolveUtils.class);

    public static Object getValueObj(Value value, Class<?> classToMatch, BeanFactory beanFactory) {
        switch (value.getValueSubType()) {
            case NULL_VALUE:
                return null;
            case PLAIN_VALUE: {
                return TypeUtils.toWrappedValue(classToMatch, value.getRawValue());
            }
            case SET_VALUE: {
                // convert to compatible set value
//                AssertUtils.assertTrue(Set.class.isAssignableFrom(classToMatch), " need set type");
                ListSetValueRef listSetValueRef = (ListSetValueRef) value;

                Set set;
                if (classToMatch.isInterface()) {
                    set = new HashSet();
                } else {
                    try {
                        set = (Set) classToMatch.newInstance();
                    } catch (Exception e) {
                        throw new BeansException("create set fail for " + classToMatch);
                    }
                }

                Class<?> elementType = listSetValueRef.getTypeForValue();

                for (ValueOrRef vr : listSetValueRef.getContents()) {
                    Object subElement = null;
                    if (vr.isValue()) {
                        subElement = getValueObj(vr, elementType, beanFactory);
                    } else {
                        subElement = getRefObj(vr, beanFactory);
                    }
                    set.add(subElement);
                }
                return set;
            }

            case LIST_VALUE: {
                ListSetValueRef listValueRef = (ListSetValueRef) value;
                Class<?> elementType = listValueRef.getTypeForValue();
                if (classToMatch.isArray()) {
                    // array
                    Object array = Array.newInstance(elementType, listValueRef.getContents().size());
                    int i = 0;
                    for (ValueOrRef vr : listValueRef.getContents()) {
                        Object subElement = null;
                        if (vr.isValue()) {
                            subElement = getValueObj(vr, elementType, beanFactory);
                        } else {
                            subElement = getRefObj(vr, beanFactory);
                        }
                        // todo, 考虑int等各种类型。。。
                        Array.set(array, i, subElement);
                        i++;
                    }

                    return array;

                } else {
                    // list
                    List list;
                    if (classToMatch.isInterface()) {
                        list = new ArrayList();
                    } else {
                        try {
                            list = (List) classToMatch.newInstance();
                        } catch (Exception e) {
                            throw new BeansException("create list fail for " + classToMatch);
                        }
                    }

                    for (ValueOrRef vr : listValueRef.getContents()) {
                        Object subElement = null;
                        if (vr.isValue()) {
                            subElement = getValueObj(vr, elementType, beanFactory);
                        } else {
                            subElement = getRefObj(vr, beanFactory);
                        }
                        list.add(subElement);
                    }
                    return list;
                }
            }
            default:
                throw new BeansException("unsupported type");
        }
    }

    public static Object getRefObj(Ref ref, BeanFactory beanFactory) {
        String requiredName = ref.getRequiredName();
        Class<?> requiredType = ref.getRequiredType();
        Object obj = null;
        if (StringUtils.isNotBlank(requiredName)) {
            try {
                obj = beanFactory.getBean(requiredName);
            } catch (BeanNotFoundException e) {
                logger.error("Bean not found for name: " + requiredName + " continue to find with class");
            }
        }
        if (obj == null) {
            try {
                obj = beanFactory.getBean(requiredType);
            } catch (BeanNotFoundException e) {
                logger.error("Bean not found for class: " + requiredType);
            }
        }
        if (obj == null && ref.isRequired()) {
            throw new BeanNotFoundException("can not found bean, name:" + requiredName + " class: " + requiredType);
        }
        return obj;
    }
}
