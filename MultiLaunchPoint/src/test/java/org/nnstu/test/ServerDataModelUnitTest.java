package org.nnstu.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.nnstu.launcher.structures.ServerDataModel;
import org.nnstu.launcher.structures.ServerStatus;
import org.nnstu.launcher.structures.immutable.ServerId;

public class ServerDataModelUnitTest {
    private ServerId serverId;
    private ServerStatus serverStatus;

    @Before
    public void init() {
        serverId = ServerId.emptyServerId();
        serverStatus = ServerStatus.STOPPED;
    }

    @Test(expected = NullPointerException.class)
    public void doEmptyArgsTest() {
        new ServerDataModel(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void doEmptyServerIdTest() {
        new ServerDataModel(null, serverStatus);
    }

    @Test(expected = NullPointerException.class)
    public void doEmptyServerStatusTest() {
        new ServerDataModel(serverId, null);
    }

    @Test
    public void doValidArgsTest() {
        new ServerDataModel(serverId, serverStatus);
    }
}
