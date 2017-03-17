package org.nnstu.launcher.structures.immutable;

import org.nnstu.contract.AbstractServer;
import org.nnstu.contract.exceptions.ServerExitException;

/**
 * This immutable class is used for representing single server instance running on it's own thread
 *
 * @author Roman Khlebnov
 */
public final class RunnableServerInstance implements Runnable {
    private final AbstractServer instance;

    public RunnableServerInstance(AbstractServer instance) {
        this.instance = instance;
    }

    public AbstractServer getInstance() {
        return instance;
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
