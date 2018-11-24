package indi.ray.miniSpring.core.context.scanner;

import indi.ray.miniSpring.core.beans.exception.InvalidXmlException;
import indi.ray.miniSpring.core.context.scanner.utils.SimpleErrorHandler;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XmlResourceLoader {

    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    private static final Logger logger = Logger.getLogger(XmlResourceLoader.class);

    private SimpleErrorHandler simpleErrorHandler = new SimpleErrorHandler(logger);

    private DocumentBuilder documentBuilder = genDocumentBuilder();

    public Element[] findRootsForGivenPaths(String... paths) {
        List<Element> elements = new ArrayList<Element>();
        for (String path : paths) {
            elements.add(getRoot(path));
        }
        return elements.toArray(new Element[elements.size()]);
    }

    private Document getDoc(String path) {
        Document document = null;
        File file = null;
        if (path.startsWith("classPath:")) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL url = cl.getResource(path.substring(10));

            if (url == null) {
                throw new InvalidXmlException("can not find xml file " + path);
            }
            file = new File(url.getPath());
        } else {
            file = new File(path);
        }
        try {
            document = documentBuilder.parse(file);
        } catch (Exception e) {
            logger.error(e);
            throw new InvalidXmlException(e.toString());
        }
        return document;
    }

    private Element getRoot(String path) {
        Document document = getDoc(path);
        Element root = document.getDocumentElement();
        return root;
    }


    private DocumentBuilder genDocumentBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
            documentBuilder.setErrorHandler(this.simpleErrorHandler);
        } catch (ParserConfigurationException e) {
            logger.info(e);
        }
        return documentBuilder;
    }
}
