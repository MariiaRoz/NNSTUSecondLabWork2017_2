package org.nnstu.test;

import org.junit.Before;
import org.junit.Test;
import org.nnstu.launcher.services.ServerLaunchService;
import org.nnstu.launcher.services.ServerLookupService;

import static org.junit.Assert.*;

/**
 * Launcher unit testing on local server stubs
 *
 * @author Roman Khlebnov
 */
public class ServerServicesUnitTest {
    private ServerLaunchService emptyInstance = null;
    private ServerLaunchService singleInstance = null;
    private ServerLaunchService multiInstance = null;

    @Before
    public void createInstanceBeforeTest() {
        emptyInstance = new ServerLaunchService(ServerLookupService.forPackage("org.nnstu.test.stubs.empty"));
        singleInstance = new ServerLaunchService(ServerLookupService.forPackage("org.nnstu.test.stubs.single"));
        multiInstance = new ServerLaunchService(ServerLookupService.forPackage("org.nnstu.test.stubs"));
    }

    @Test(expected = NullPointerException.class)
    public void doWrongServerLookupServiceInitializationTest() {
        assertEquals(null, new ServerLaunchService(ServerLookupService.forPackage(null)).getServerLookupService());
    }

    @Test
    public void doCorrectServerLookupServiceInitializationTest() {
        assertNotEquals(null, new ServerLaunchService(ServerLookupService.forPackage("org.nnstu.test.stubs")).getServerLookupService());
    }

    @Test(expected = InstantiationException.class)
    public void doEmptyLaunchTest() throws InstantiationException {
        emptyInstance.simultaneousLaunch();
        assertEquals("Check that no servers were found.", emptyInstance.getServerLookupService().getServerInstances().size(), 0, 0);
        assertFalse("Check that service is not locked after launch.", emptyInstance.isLaunchingLocked());

        emptyInstance.stopExecution();

        assertFalse("Check that service is still not locked after stop.", emptyInstance.isLaunchingLocked());
    }

    @Test
    public void doSingleLaunchTest() throws InstantiationException {
        singleInstance.simultaneousLaunch();
        assertEquals("Check that 1 server was found.", singleInstance.getServerLookupService().getServerInstances().size(), 1, 0);
        assertTrue("Check that service is locked after launch.", singleInstance.isLaunchingLocked());

        singleInstance.stopExecution();

        assertFalse("Check that service is not locked after stop.", singleInstance.isLaunchingLocked());
    }

    @Test
    public void doMultiLaunchTest() throws InstantiationException {
        multiInstance.simultaneousLaunch();
        assertEquals("Check that 2 servers were found.", multiInstance.getServerLookupService().getServerInstances().size(), 2, 0);
        assertTrue("Check that service is locked after launch.", multiInstance.isLaunchingLocked());

        multiInstance.stopExecution();

        assertFalse("Check that service is not locked after stop.", multiInstance.isLaunchingLocked());
    }

    @Test
    public void doSingleChoiceLaunchTest() throws InstantiationException {
        multiInstance.pinpointLaunch(666);
        assertEquals("Check that 2 servers were found.", multiInstance.getServerLookupService().getServerInstances().size(), 2, 0);
        assertTrue("Check that service is locked after launch.", multiInstance.isLaunchingLocked());

        multiInstance.stopExecution();

        assertFalse("Check that service is not locked after stop.", multiInstance.isLaunchingLocked());
    }
}
