package indi.ray.miniSpring.core.functionTest.annotations.getPrototype.test;

import indi.ray.miniSpring.core.functionTest.annotations.getPrototype.Prototype;
import indi.ray.miniSpring.core.annotations.ComponentScan;
import indi.ray.miniSpring.core.testBase.BaseConfigurableTester;
import indi.ray.miniSpring.core.utils.AssertUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

@ComponentScan(basePackageClasses = {Prototype.class})
public class PrototypeTester extends BaseConfigurableTester {
    @Test
    // get single prototype many time, each bean should not be the same
    public void testGetPrototypeWithName() {
        Set<Prototype> prototypes = new HashSet<Prototype>(8);
        for (int i = 0; i < 3; i++) {
            Prototype prototype = applicationContext.getBean("prototype", Prototype.class);
            prototypes.add(prototype);
        }
        AssertUtils.assertTrue(prototypes.size() == 3, "应当得到3个不同的bean");
        System.out.println(prototypes);
    }

    @Test
    public void testGetPrototypeWithType() {
        Set<Prototype> prototypes = new HashSet<Prototype>(8);
        for (int i = 0; i < 3; i++) {
            Prototype prototype = applicationContext.getBean(Prototype.class);
            prototypes.add(prototype);
        }
        AssertUtils.assertTrue(prototypes.size() == 3, "应当得到3个不同的bean");
        System.out.println(prototypes);
    }
}
