package indi.ray.annotationSpring.core.testBase;

import indi.ray.annotationSpring.core.context.ComponentScanApplicationContext;
import org.apache.log4j.Logger;

/**
 * Abstract base class for tester that can be used as {@code @ComponentScan} information provider
 */
public abstract class BaseConfigurableTester extends BaseTester{
    // default application context, using Test class as configuration
    protected ComponentScanApplicationContext applicationContext = new ComponentScanApplicationContext(this.getClass());

}
