package org.nnstu.test;

import org.junit.Test;
import org.nnstu.launcher.services.ServerLookupService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ServerLookupServiceUnitTest {
    @Test
    public void doNullTest() {
        assertEquals(null, ServerLookupService.forPackage(null));
    }

    @Test
    public void doEmptyStringTest() {
        assertEquals(null, ServerLookupService.forPackage(""));
    }

    @Test
    public void doValidStringTest() {
        assertNotEquals(null, ServerLookupService.forPackage("org.nnstu.test.stubs"));
    }
}
