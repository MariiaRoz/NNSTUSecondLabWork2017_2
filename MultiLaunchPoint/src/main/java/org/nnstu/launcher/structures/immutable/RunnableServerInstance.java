package org.nnstu.launcher.structures.immutable;

import lombok.Value;
import org.nnstu.contract.AbstractServer;
import org.nnstu.contract.exceptions.ServerExitException;

/**
 * This immutable class is used for representing single server instance running on it's own thread
 *
 * @author Roman Khlebnov
 */
@Value
public class RunnableServerInstance implements Runnable {
    AbstractServer instance;

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
