package org.nnstu.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.nnstu.contract.AbstractServer;
import org.nnstu.contract.exceptions.ServerExitException;
import org.nnstu.launcher.structures.immutable.RunnableServerInstance;
import org.nnstu.test.stubs.ServerStub;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class RunnableServerInstanceUnitTest {
    private AbstractServer serverMock;

    @Before
    public void createMock() {
        serverMock = Mockito.spy(new ServerStub());
    }

    @Test(expected = NullPointerException.class)
    public void doNullInstanceTest() {
        assertEquals(null, new RunnableServerInstance(null).getInstance());
    }

    @Test
    public void doStubInstanceTest() {
        assertNotEquals(null, new RunnableServerInstance(serverMock).getInstance());
    }

    @Test(expected = InvocationTargetException.class)
    public void doLaunchDelegationTest() {
        Mockito.doThrow(InvocationTargetException.class).when(serverMock).launchServer();
        RunnableServerInstance test = new RunnableServerInstance(serverMock);
        test.run();
    }

    @Test(expected = InvocationTargetException.class)
    public void doStopDelegationTest() throws ServerExitException {
        Mockito.doThrow(InvocationTargetException.class).when(serverMock).stopServer();
        RunnableServerInstance test = new RunnableServerInstance(serverMock);
        test.stop();
    }
}
