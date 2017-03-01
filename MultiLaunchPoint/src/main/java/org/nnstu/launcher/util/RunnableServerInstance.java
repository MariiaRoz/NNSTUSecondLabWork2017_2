package org.nnstu.launcher.util;

import org.nnstu.contract.AbstractServer;
import org.nnstu.contract.ServerExitException;

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

    @Override
    public void run() {
        if (instance != null) {
            instance.launchServer();
        }
    }

    public void stop() throws ServerExitException {
        if (instance != null) {
            instance.stopServer();
        }
    }
}
