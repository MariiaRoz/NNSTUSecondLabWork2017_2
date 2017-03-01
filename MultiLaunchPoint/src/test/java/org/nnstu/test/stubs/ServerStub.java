package org.nnstu.test.stubs;

import org.nnstu.contract.AbstractServer;
import org.nnstu.contract.ServerExitException;

public class ServerStub extends AbstractServer {
    public ServerStub() {
        super(666);
    }

    @Override
    public void launchServer() {
        System.out.println("ServerStub was launched at port " + getServerPort() + ".");
    }

    @Override
    public void stopServer() throws ServerExitException {
        System.out.println("ServerStub at port " + getServerPort() + " was stopped.");
    }
}
