package com.ray.miniSpring.core.context.scanner;

import com.ray.miniSpring.core.beans.definition.AutowireType;
import com.ray.miniSpring.core.beans.definition.BeanDefinition;
import com.ray.miniSpring.core.beans.exception.InvalidXmlException;
import com.ray.miniSpring.core.context.scanner.utils.XmlParsingUtils;
import com.ray.miniSpring.core.utils.AssertUtils;
import com.ray.miniSpring.core.beans.definition.BeanConstructor;
import com.ray.miniSpring.core.beans.definition.BeanProperty;
import com.ray.miniSpring.core.beans.definition.StaticBeanConstructorResolver;
import com.ray.miniSpring.core.beans.definition.BeanPropertyResolver;
import com.ray.miniSpring.core.beans.definition.StaticValueRefResolver;
import com.ray.miniSpring.core.beans.definition.ScopeEnum;
import com.ray.miniSpring.core.beans.definition.XmlBeanDefinition;
import com.ray.miniSpring.core.utils.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XmlBeanDefinitionProvider {
    public static final String ATTRIBUTE_NAME = "name";

    public static final String ATTRIBUTE_ID = "id";

    public static final String ATTRIBUTE_CLASS = "class";

    public static final String ATTRIBUTE_AUTOWIRED = "autowired";

    public static final String ATTRIBUTE_AUTOWIRED_BYNAME = "byName";

    public static final String ATTRIBUTE_AUTOWIRED_BYTYPE = "byType";

    public static final String ATTRIBUTE_AUTOWIRED_DEFAULT = "default";

    public static final String ATTRIBUTE_PRIMARY = "primary";

    public static final String ATTRIBUTE_LAZYINIT = "lazy-init";

    public static final String ATTRIBUTE_SCOPE = "scope";

    public static final String ATTRIBUTE_DEPENDON = "depends-on";

    public static final String ELEMENT_CONSTRUCTOR_ARG = "constructor-arg";

    public static final String ELEMENT_PROPERTY = "property";

    public static final String ELEMENT_BEANS = "beans";

    public static final String ELEMENT_BEAN = "bean";

    public static final String ELEMENT_REF = "ref";

    public static final String ELEMENT_VALUE = "value";

    public static final String ELEMENT_NULL = "null";

    public static final String ELEMENT_LIST = "list";

    public static final String ELEMENT_SET = "set";

    public static final String ATTRIBUTE_INDEX = "index";

    public static final String ATTRIBUTE_TYPE = "type";

    public static final String ATTRIBUTE_VALUE = "value";

    public static final String ATTRIBUTE_VALUE_REF = "value-ref";

    private StaticValueRefResolver        staticValueRefResolver;
    private StaticBeanConstructorResolver staticBeanConstructorResolver;
    private BeanPropertyResolver          beanPropertyResolver;
    private XmlResourceLoader             resourceLoader     = new XmlResourceLoader();
    private AutowireType                  beansAutowiredType = AutowireType.No;
    private List<BeanDefinition>          beanDefinitionList = new ArrayList(16);
    private Set<String>                   beanNames          = new HashSet<String>(16);
    private Map<String, List<String>>     beanNamesForClass  = new HashMap<String, List<String>>(16);

    public XmlBeanDefinitionProvider() {
        staticValueRefResolver = new StaticValueRefResolver(this);
        staticBeanConstructorResolver = new StaticBeanConstructorResolver(staticValueRefResolver);
        beanPropertyResolver = new BeanPropertyResolver(staticValueRefResolver);
    }

    public List<BeanDefinition> findBeanDefinitions(String... paths) {
        Element[] roots = this.resourceLoader.findRootsForGivenPaths(paths);
        for (Element root : roots) {
            if (XmlParsingUtils.isValidElementForName(root, ELEMENT_BEANS)) {
                //set global settings
                List<BeanDefinition> bds = readBeans(root);
            }
        }
        return beanDefinitionList;
    }

    public List<BeanDefinition> readBeans(Element root) {
        AssertUtils.assertTrue(XmlParsingUtils.nodeNameEquals(root, ELEMENT_BEANS), "only beans are available here");
        String autoWired = root.getAttribute(ATTRIBUTE_AUTOWIRED);
        this.beansAutowiredType = AutowireType.getAutowireTypeByCode(autoWired);

        NodeList nodeList = root.getChildNodes();
        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (XmlParsingUtils.isValidElementForName(node, ELEMENT_BEAN)) {
                Element element = (Element) node;
                BeanDefinition bd = readBeanDefinition(element, !element.hasAttribute(ATTRIBUTE_ID));
                beanDefinitions.add(bd);
            }
        }
        return beanDefinitions;
    }

    public XmlBeanDefinition readBeanDefinition(Element root, boolean isAnnonymous) {
        if (root == null) return null;

        XmlBeanDefinition bd = new XmlBeanDefinition();
        bd.setAnnonymous(isAnnonymous);

        // bean name & class name
        String beanId = root.getAttribute(ATTRIBUTE_ID);
        String beanClassName = root.getAttribute(ATTRIBUTE_CLASS);

        if (StringUtils.isBlank(beanClassName)) {
            throw new InvalidXmlException("Class name not found " + root);
        }
        if (StringUtils.isBlank(beanId) || isAnnonymous) {
            beanId = StringUtils.unCapitalize(beanClassName);
            List<String> beanNames = this.beanNamesForClass.get(beanClassName);
            int tail = beanNames == null ? 0 : beanNames.size();
            beanId += "#" + tail;
        }
        bd.setBeanName(beanId);
        bd.setBeanClassName(beanClassName);
        Class<?> clazz;

        try {
            clazz = Class.forName(beanClassName);
            bd.setBeanClass(clazz);
        } catch (ClassNotFoundException e) {
            throw new InvalidXmlException("Class not found " + root);
        }

        registerBean(bd, beanId, clazz);

        // attributes

        // autowired
        if (root.hasAttribute(ATTRIBUTE_AUTOWIRED)) {
            String autowiredCode = root.getAttribute(ATTRIBUTE_AUTOWIRED);
            AutowireType autowireType = AutowireType.getAutowireTypeByCode(autowiredCode);
            if (autowireType == null) {
                if (!autowiredCode.equals(ATTRIBUTE_AUTOWIRED_DEFAULT)) {
                    throw new InvalidXmlException("Autowired type is not valid " + root);
                }
                autowireType = this.beansAutowiredType;
            }
            bd.setAutowireType(autowireType);
        }

        // primary
        bd.setPrimary(Boolean.parseBoolean(root.getAttribute(ATTRIBUTE_PRIMARY)));

        // lazy_init
        bd.setLazyInit(Boolean.parseBoolean(root.getAttribute(ATTRIBUTE_LAZYINIT)));

        // scope
        bd.setScopeEnum(ScopeEnum.getScopeEnumByCode(root.getAttribute(ATTRIBUTE_SCOPE)));

        // depends on
        if (root.hasAttribute(ATTRIBUTE_DEPENDON)) {
            String[] dependsOn = root.getAttribute(ATTRIBUTE_DEPENDON).split(",");
            bd.setDependsOnBeanNames(dependsOn);
        }

        // child nodes

        NodeList nodeList = root.getChildNodes();

        processConstructorArgs(nodeList, bd);

        processProperties(nodeList, bd);

        return bd;
    }


    private void registerBean(BeanDefinition bd, String beanName, Class<?> clazz) {
        AssertUtils.assertTrue(this.beanNames.add(beanName), "beanName " + beanName + "should be unique");

        this.beanDefinitionList.add(bd);
        List<String> beanNames = this.beanNamesForClass.get(clazz);
        if (beanNames == null) {
            beanNames = new ArrayList<String>();
            this.beanNamesForClass.put(clazz.getName(), beanNames);
        }
        beanNames.add(beanName);
    }

    private void processConstructorArgs(NodeList nodeList, XmlBeanDefinition bd) {
        BeanConstructor bc = new BeanConstructor();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (XmlParsingUtils.isValidElementForName(node, ELEMENT_CONSTRUCTOR_ARG)) {
                this.staticBeanConstructorResolver.addConstructorArg((Element) node, bc);
            }
        }
        this.staticBeanConstructorResolver.resolveConstructor(bd.getBeanClass(), bc, false);
        bd.setConstructor(bc);
    }

    private void processProperties(NodeList nodeList, XmlBeanDefinition bd) {
        Class<?> clazz = bd.getBeanClass();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (XmlParsingUtils.isValidElementForName(node, ELEMENT_PROPERTY)) {
                BeanProperty bp = this.beanPropertyResolver.resolveBeanField((Element) node, clazz);
                bd.getBeanProperties().add(bp);
            }
        }
    }

    public StaticValueRefResolver getStaticValueRefResolver() {
        return staticValueRefResolver;
    }
}
