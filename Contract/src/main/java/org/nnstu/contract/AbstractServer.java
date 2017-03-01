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
    private final int serverPort;

    public AbstractServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    /**
     * Utility method to retrieve Maven module name of the current server class, can't be overridden
     *
     * @return {@link String} that represents Maven module of the current class
     */
    public final String getServerMavenModuleName() {
        String packageName = getClass().getPackage().getName().split("\\.")[1];
        return "Project" + packageName.substring(packageName.length() - 1);
    }

    // This method should be main in your server instance and has to be overridden with your custom logic.
    public abstract void launchServer();

    // This method should serve as a stopping power for your server and has to be overridden with your custom logic.
    public abstract void stopServer() throws ServerExitException;
}
