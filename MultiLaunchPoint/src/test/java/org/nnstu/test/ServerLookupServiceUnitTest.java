package org.nnstu.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.nnstu.launcher.ServerLookupService;
import org.nnstu.test.stubs.ServerStub;
import org.nnstu.test.stubs.single.SingleServerStub;

import static org.junit.Assert.*;

/**
 * Launcher unit testing on local server stubs
 *
 * @author Roman Khlebnov
 */
public class ServerLookupServiceUnitTest {
    private ServerLookupService emptyInstance = null;
    private ServerLookupService singleInstance = null;
    private ServerLookupService multiInstance = null;

    @Before
    public void createInstanceBeforeTest() {
        emptyInstance = new ServerLookupService("org.nnstu.test.stubs.empty");
        singleInstance = new ServerLookupService("org.nnstu.test.stubs.single");
        multiInstance = new ServerLookupService("org.nnstu.test.stubs");
    }

    @Test
    public void emptyLaunchTest() {
        String launchResult = emptyInstance.simultaneousLaunch();
        assertTrue(
                "Check if no instances were started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        "No servers found, exiting."
                )
        );
        assertEquals("Check that no servers were found.", emptyInstance.getAllServerInstances().size(), 0, 0);
        assertFalse("Check that service is not locked after launch.", emptyInstance.isLaunchingLocked());

        emptyInstance.stopExecution();

        assertFalse("Check that service is still not locked after stop.", emptyInstance.isLaunchingLocked());
    }

    @Test
    public void singleLaunchTest() {
        String launchResult = singleInstance.simultaneousLaunch();
        assertTrue(
                "Check if the single instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        ":" + new SingleServerStub().getServerPort()
                )
        );
        assertEquals("Check that 1 server was found.", singleInstance.getAllServerInstances().size(), 1, 0);
        assertTrue("Check that service is locked after launch.", singleInstance.isLaunchingLocked());

        singleInstance.stopExecution();

        assertFalse("Check that service is not locked after stop.", singleInstance.isLaunchingLocked());
    }

    @Test
    public void multiLaunchTest() {
        String launchResult = multiInstance.simultaneousLaunch();
        assertTrue(
                "Check if the SingleServerStub instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        ":" + new SingleServerStub().getServerPort()
                )
        );
        assertTrue(
                "Check if the ServerStub instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        ":" + new ServerStub().getServerPort()
                )
        );
        assertEquals("Check that 2 servers were found.", multiInstance.getAllServerInstances().size(), 2, 0);
        assertTrue("Check that service is locked after launch.", multiInstance.isLaunchingLocked());

        multiInstance.stopExecution();

        assertFalse("Check that service is not locked after stop.", multiInstance.isLaunchingLocked());
    }

    @Test
    public void singleChoiceLaunchTest() {
        String launchResult = multiInstance.launchSingleInstance(666);
        assertFalse(
                "Check if the SingleServerStub instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        ":" + new SingleServerStub().getServerPort()
                )
        );
        assertTrue(
                "Check if the ServerStub instance was started.",
                StringUtils.containsIgnoreCase(
                        launchResult,
                        ":" + new ServerStub().getServerPort()
                )
        );
        assertEquals("Check that 2 servers were found.", multiInstance.getAllServerInstances().size(), 2, 0);
        assertTrue("Check that service is locked after launch.", multiInstance.isLaunchingLocked());

        multiInstance.stopExecution();

        assertFalse("Check that service is not locked after stop.", multiInstance.isLaunchingLocked());
    }
}
