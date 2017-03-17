package org.nnstu.test;

import org.junit.Test;
import org.nnstu.launcher.structures.immutable.ServerId;

public class ServerIdUnitTest {
    private String className = "Test";
    private String moduleName = "org.test";

    @Test(expected = NullPointerException.class)
    public void doEmptyArgsInitTest() {
        new ServerId(0, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void doEmptyClassNameInitTest() {
        new ServerId(0, null, moduleName);
    }

    @Test(expected = NullPointerException.class)
    public void doEmptyModuleNameInitTest() {
        new ServerId(0, className, null);
    }

    @Test
    public void doValidArgsInitTest() {
        new ServerId(0, className, moduleName);
    }
}
