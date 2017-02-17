package org.nnstu.test.stubs;

import org.nnstu.contract.AbstractServer;

public class ServerStub extends AbstractServer {
    public ServerStub() {
        super(666);
    }

    @Override
    public void launchServer() {
        System.out.println("ServerStub was launched at port " + getServerPort() + ".");
    }

    @Override
    public void stopServer() throws Exception {
        System.out.println("ServerStub at port " + getServerPort() + " was stopped.");
    }
}
