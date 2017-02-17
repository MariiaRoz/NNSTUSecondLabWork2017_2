package org.nnstu.contract;

/**
 * This class is has to be overridden by your server instances in order to perform
 * simultaneous launch of all servers.
 *
 * @author Roman Khlebnov
 */
public abstract class AbstractServer {
    // Mandatory field with server port.
    // We assume that all instances will be launched on the localhost with different ports.
    private final int SERVER_PORT;

    public AbstractServer(int serverPort) {
        this.SERVER_PORT = serverPort;
    }

    public int getServerPort() {
        return SERVER_PORT;
    }

    // This method should be main in your server instance and has to be overridden with your custom logic.
    public abstract void launchServer();

    // This method should serve as a stopping power for your server and has to be overridden with your custom logic.
    public abstract void stopServer() throws Exception;
}
