package org.nnstu.launcher.util;

import org.nnstu.contract.AbstractServer;

/**
 * This class is used for representing single server instance running on it's own thread
 *
 * @author Roman Khlebnov
 */
public class RunnableServerInstance implements Runnable {
    private final AbstractServer instance;

    public RunnableServerInstance(AbstractServer instance) {
        this.instance = instance;
    }

    public ServerId getServerId() {
        return new ServerId(
                instance.getServerPort()
                , instance.getClass().getSimpleName()
                , instance.getServerMavenModuleName()
        );
    }

    public void run() {
        if (instance != null) {
            instance.launchServer();
        }
    }

    public void stop() throws Exception {
        if (instance != null) {
            instance.stopServer();
        }
    }
}
