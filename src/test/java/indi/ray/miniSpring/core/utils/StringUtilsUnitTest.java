package indi.ray.miniSpring.core.utils;

import org.junit.Test;

public class StringUtilsUnitTest {
    @Test
    public void testIsNotBlank() {
        AssertUtils.assertTrue(!StringUtils.isNotBlank(""),"");
        AssertUtils.assertTrue(!StringUtils.isNotBlank(" "),"");
        AssertUtils.assertTrue(!StringUtils.isNotBlank("    "),"");
        AssertUtils.assertTrue(StringUtils.isNotBlank(" asdf  asf "),"");
    }

    @Test
    public void testIsBlank() {
        AssertUtils.assertTrue(StringUtils.isBlank(""),"");
        AssertUtils.assertTrue(StringUtils.isBlank(" "),"");
        AssertUtils.assertTrue(StringUtils.isBlank("    "),"");
        AssertUtils.assertTrue(!StringUtils.isBlank(" asdf  asf "),"");
    }
}
