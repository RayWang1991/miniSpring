package indi.ray.miniSpring.core.beans.definition;


import indi.ray.miniSpring.core.beans.exception.InvalidXmlException;
import indi.ray.miniSpring.core.beans.utils.TypeUtils;
import indi.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider;
import indi.ray.miniSpring.core.context.scanner.utils.XmlParsingUtils;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static indi.ray.miniSpring.core.context.scanner.XmlBeanDefinitionProvider.*;


public class StaticValueRefResolver {

    private static final Logger logger = Logger.getLogger(StaticValueRefResolver.class);

    private XmlBeanDefinitionProvider beanDefinitionProvider;

    public StaticValueRefResolver(XmlBeanDefinitionProvider xmlBeanDefinitionProvider) {
        this.beanDefinitionProvider = xmlBeanDefinitionProvider;
    }


    /**
     * 对 constructor-arg 元素适用，解析其相关联的引用或者值,
     * 限制至多只能有1个值或引用相关联
     *
     * @param element 元素
     * @return
     */
    public ValueOrRef resolveRefValueForConstructorArg(Element element) {
        NodeList nodeList = element.getChildNodes();
        if (nodeList == null || nodeList.getLength() == 0) {
            return null;
        }
        Element subElement = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (XmlParsingUtils.isCandidateElement(node)) {
                if (subElement != null) {
                    throw new InvalidXmlException("constructor-arg只能有一个子元素");
                } else {
                    subElement = (Element) node;
                }
            }
        }
        return resolveElementByName(subElement.getNodeName(), subElement);
    }

    public ValueOrRef resolveRefValueForProperty(Element element) {
        if (element.hasAttribute(ATTRIBUTE_VALUE)) {
            return DefaultValueOrRef.plainValue(null, element.getAttribute(ATTRIBUTE_VALUE));
        } else if (element.hasAttribute(ATTRIBUTE_VALUE_REF)) {
            return DefaultValueOrRef.ref(null, element.getAttribute(ATTRIBUTE_VALUE_REF), true);
        } else {
            NodeList nodeList = element.getChildNodes();
            if (nodeList == null || nodeList.getLength() == 0) {
                return null;
            }
            AssertUtils.assertTrue(nodeList.getLength() == 1, "property只能有一个子元素");
            Element subElement = (Element) nodeList.item(0);
            return resolveElementByName(subElement.getNodeName(), subElement);
        }
    }

    public ValueOrRef resolveElementByName(String name, Element element) {
        if (name.equals(ELEMENT_REF)) {
            return readRef(element);
        } else if (name.equals(ELEMENT_VALUE)) {
            return readValue(element);
        } else if (name.equals(ELEMENT_NULL)) {
            return readNull();
        } else if (name.equals(ELEMENT_SET)) {
            return readSet(element);
        } else if (name.equals(ELEMENT_LIST)) {
            return readList(element);
        } else if (name.equals(ELEMENT_BEAN)) {
            return readBean(element);
        } else {
            throw new InvalidXmlException("unsupported element " + name);
        }
    }

    /** ref, no class info, is required */
    public ValueOrRef readRef(Element element) {
        String ref = element.getTextContent();
        ValueOrRef defaultValueOrRef = DefaultValueOrRef.ref(null, ref, true);
        return defaultValueOrRef;
    }

    /** plainValue, no class info */
    public ValueOrRef readValue(Element element) {
        String value = element.getTextContent();
        ValueOrRef defaultValueOrRef = DefaultValueOrRef.plainValue(null, value);
        return defaultValueOrRef;
    }

    /** list */
    public ValueOrRef readList(Element element) {
        ListSetValueRef listValueRef = DefaultValueOrRef.listValue();
        // type of elements
        Class<?> clazz = TypeUtils.typeForName(element.getAttribute(ATTRIBUTE_TYPE));
        listValueRef.setType(clazz);
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (XmlParsingUtils.isCandidateElement(node)) {
                Element sub = (Element) node;
                listValueRef.getContents().add(resolveElementByName(sub.getNodeName(), sub));
            }
        }
        return listValueRef;
    }

    /** set */
    public ValueOrRef readSet(Element element) {
        ListSetValueRef setValueRef = DefaultValueOrRef.setValue();
        // type of elements
        Class<?> clazz = TypeUtils.typeForName(element.getAttribute(ATTRIBUTE_TYPE));
        setValueRef.setType(clazz);
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (XmlParsingUtils.isCandidateElement(nodeList.item(i))) {
                Element sub = (Element) nodeList.item(i);
                setValueRef.getContents().add(resolveElementByName(sub.getNodeName(), sub));
            }
        }
        return setValueRef;
    }

    /** null */
    public ValueOrRef readNull() {
        return DefaultValueOrRef.nullValue();
    }

    /** bean */
    public ValueOrRef readBean(Element element) {
        BeanDefinition bd = this.beanDefinitionProvider.readBeanDefinition(element, true);
        ValueOrRef beanRef = DefaultValueOrRef.ref(bd.getBeanClass(), bd.getBeanName(), true);
        return beanRef;
    }
}
