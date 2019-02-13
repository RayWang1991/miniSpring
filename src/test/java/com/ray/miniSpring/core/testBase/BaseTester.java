package com.ray.miniSpring.core.testBase;

import org.apache.log4j.Logger;

/**
 * Base class for tester, extract logger...
 */
public abstract class BaseTester {

    /** logger, can be used for subclasses */
    protected final Logger logger = Logger.getLogger(this.getClass());
}
