package org.nnstu.test.stubs.single;

import org.nnstu.contract.AbstractServer;

public class SingleServerStub extends AbstractServer {
    public SingleServerStub() {
        super(1337);
    }

    @Override
    public void launchServer() {
        System.out.println("Server was launched at port " + getServerPort() + ".");
    }

    @Override
    public void stopServer() throws Exception {
        System.out.println("Server at port " + getServerPort() + " was stopped.");
    }
}
