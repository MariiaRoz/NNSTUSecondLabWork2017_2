package org.nnstu.contract;

import org.apache.log4j.Logger;

/**
 * This class is has to be overridden by your server instances in order to perform
 * simultaneous launch of all servers.
 *
 * @author Roman Khlebnov
 */
public abstract class AbstractServer {
    // Logger is already available on server side for usage.
    protected final Logger log = Logger.getLogger(getClass());

    // Mandatory field with server port.
    // We assume that all instances will be launched on the localhost with different ports.
    private final int serverPort;

    /**
     * This constructor has to be overridden.
     * In your class (which must extend current one) must be only one constructor without arguments as shown below:
     * <p>
     * <pre>
     * {@code
     * public YourServerClassName() {
     *     super(12345); // Port sample is 12345
     * }
     * }
     * </pre>
     *
     * @param serverPort - {@link Integer}, representing server port.
     */
    public AbstractServer(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * {@link AbstractServer#serverPort} getter, you don't need to reimplement this in your class.
     *
     * @return {@link Integer} representing your server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * Utility method to retrieve Maven module name of the current server class, can't be overridden
     *
     * @return {@link String} that represents Maven module of the current class
     */
    public final String getServerMavenModuleName() {
        String packageNumber = getClass().getPackage().getName().split("\\.")[1];
        return "Project" + packageNumber.substring(packageNumber.length() - 1);
    }

    // This method should be main in your server instance and has to be overridden with your custom logic.
    public abstract void launchServer();

    // This method should serve as a stopping power for your server and has to be overridden with your custom logic.
    public abstract void stopServer() throws ServerExitException;
}
