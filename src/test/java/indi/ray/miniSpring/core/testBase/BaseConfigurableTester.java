package indi.ray.miniSpring.core.testBase;

import indi.ray.miniSpring.core.context.AnnotationApplicationContext;

/**
 * Abstract base class for tester that can be used as {@code @ComponentScan} information provider
 */
public abstract class BaseConfigurableTester extends BaseTester{
    // default application context, using Test class as configuration
    protected AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(this.getClass());

}
