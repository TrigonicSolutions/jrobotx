package net.sourceforge.jrobotx.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompareUtilsTest {
    @Test
    public void testCompareBools() {
        assertTrue(CompareUtils.compare(false, true) < 0);
        assertTrue(CompareUtils.compare(false, false) == 0);
        assertTrue(CompareUtils.compare(true, true) == 0);
        assertTrue(CompareUtils.compare(true, false) > 0);
    }
}
