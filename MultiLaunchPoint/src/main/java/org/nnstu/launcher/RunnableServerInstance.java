package org.nnstu.launcher;

import org.nnstu.contract.AbstractServer;

/**
 * This class is used for representing single server instance running on it's own thread
 *
 * @author Roman Khlebnov
 */
public class RunnableServerInstance implements Runnable {
    private final AbstractServer instance;

    RunnableServerInstance(AbstractServer instance) {
        this.instance = instance;
    }

    AbstractServer getServerInstance() {
        return instance;
    }

    public void run() {
        if (instance != null) {
            instance.launchServer();
        }
    }

    void stop() throws Exception {
        if (instance != null) {
            instance.stopServer();
        }
    }
}
