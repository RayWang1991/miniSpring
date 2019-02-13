package com.ray.miniSpring.core.context.scanner.utils;


import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlParsingUtils {
    public static boolean isCandidateElement(Node node) {
        return node instanceof Element;
    }

    public static boolean nodeNameEquals(Node node, String nameToTest) {
        return node.getNodeName().equals(nameToTest);
    }

    public static boolean isValidElementForName(Node node, String name) {
        return isCandidateElement(node) && nodeNameEquals(node, name);
    }
}
