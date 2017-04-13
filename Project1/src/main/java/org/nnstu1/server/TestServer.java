package org.nnstu1.server;

import org.nnstu.contract.AbstractServer;
import org.nnstu.contract.exceptions.ServerExitException;

public class TestServer extends AbstractServer {

    public TestServer() {
        super(6666);
    }

    @Override
    public void launchServer() {
        log.error("LAUNCHED");
    }

    @Override
    public void stopServer() throws ServerExitException {
        log.error("STOPPED");
    }
}
