package org.nnstu.test.stubs.single;

import org.nnstu.contract.AbstractServer;

public class SingleServerStub extends AbstractServer {
    public SingleServerStub() {
        super(1337);
    }

    @Override
    public void launchServer() {
        System.out.println("SingleServerStub was launched at port " + getServerPort() + ".");
    }

    @Override
    public void stopServer() throws Exception {
        System.out.println("SingleServerStub at port " + getServerPort() + " was stopped.");
    }
}
