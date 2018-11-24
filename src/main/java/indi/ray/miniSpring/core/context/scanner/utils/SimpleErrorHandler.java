package indi.ray.miniSpring.core.context.scanner.utils;

import com.sun.org.apache.xml.internal.utils.DefaultErrorHandler;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class SimpleErrorHandler extends DefaultErrorHandler {
    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public SimpleErrorHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        super.warning(exception);
        logger.warn(exception);
    }
}
