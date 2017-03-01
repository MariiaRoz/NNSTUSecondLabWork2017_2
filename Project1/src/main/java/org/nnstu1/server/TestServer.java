package org.nnstu1.server;

import org.nnstu.contract.AbstractServer;

public class TestServer extends AbstractServer{

    public TestServer() {
        super(8089);
    }

    @Override
    public void launchServer() {
        System.out.println("lul");
    }

    @Override
    public void stopServer() throws Exception {
        System.out.println("kek");
    }

}
