package indi.ray.miniSpring.core.utils;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class AssertUtilsUnitTest {
    @Test
    public void testAssertNotNull() {
        boolean hasException = false;
        try {
            AssertUtils.assertNotNull(null, "this should trigger exceptions");
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        assert hasException;
    }

    @Test
    public void testAssertTrue() {
        boolean hasException = false;
        try {
            AssertUtils.assertTrue(false, "this should trigger exceptions");
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        assert hasException;
    }

}

