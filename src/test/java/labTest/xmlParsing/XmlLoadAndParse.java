package labTest.xmlParsing;

import indi.ray.miniSpring.core.context.scanner.utils.SimpleErrorHandler;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class XmlLoadAndParse {

    private static final Logger logger = Logger.getLogger(XmlLoadAndParse.class);

    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    @Test
    public void test() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
            documentBuilder.setErrorHandler(new SimpleErrorHandler(logger));
        } catch (ParserConfigurationException e) {
            logger.info(e);
        }

        String path = "/Users/raywang/IdeaProjects/miniSpring/src/test/resources/xmlTest/test.xml";
//        String path = "/Users/raywang/IdeaProjects/miniSpring/src/test/resources/xmlTest/sample.xml";
        File file = new File(path);
        Document document = null;
        try {
            document = documentBuilder.parse(file);
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
        Element root = document.getDocumentElement();
        logger.info(document);
        logger.info(root);
    }
}
