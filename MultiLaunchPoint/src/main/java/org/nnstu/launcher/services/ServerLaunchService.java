package org.nnstu.launcher.services;

import org.apache.log4j.Logger;
import org.nnstu.launcher.structures.immutable.RunnableServerInstance;
import org.nnstu.launcher.util.ConversionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ServerLaunchService {
    private static final Logger log = Logger.getLogger(ServerLaunchService.class);
    private final ServerLookupService serverLookupService;
    private ExecutorService executorService = null;

    public ServerLaunchService(ServerLookupService serverLookupService) {
        Objects.requireNonNull(serverLookupService);

        this.serverLookupService = serverLookupService;
    }

    public ServerLookupService getServerLookupService() {
        return serverLookupService;
    }

    /**
     * Method-decorator for simultaneous launch of all servers existing in the project
     *
     * @return {@link String} that contains all events occurred during launch phase
     */
    public void simultaneousLaunch() throws InstantiationException {
        launch(LaunchMode.SIMULTANEOUS);
    }

    /**
     * Method-decorator to launch several selected servers
     *
     * @param ports {@link Integer} ports of the requested servers
     * @return {@link String} that contains all events occurred during launch phase
     */
    public void pinpontLaunch(int... ports) throws InstantiationException {
        launch(LaunchMode.PINPOINT, ports);
    }

    /**
     * Hidden method to perform most heavy lifting tasks and verifying.
     *
     * @param mode  - {@link LaunchMode}
     * @param ports - {@link Integer} ports of selected servers
     * @return {@link String} that contains all events occurred during launch phase
     */
    private void launch(LaunchMode mode, int... ports) throws InstantiationException {
        // Different checks and tasks preparing
        if (serverLookupService.getServerInstances().isEmpty()) {
            throw new InstantiationException("Servers list is empty, cannot perform launch.");
        }

        Set<RunnableServerInstance> tasks = new HashSet<>();

        if (mode.equals(LaunchMode.SIMULTANEOUS)) {
            log.debug("Trying to perform simultaneous launch of servers.");
            tasks.addAll(serverLookupService.getServerInstances().values());
        } else if (mode.equals(LaunchMode.PINPOINT)) {
            if (ports.length == 0) {
                throw new InstantiationException("Cannot perform point launch, ports were not provided.");
            }

            log.debug("Trying to perform pinpoint launch of selected servers.");

            tasks.addAll(
                    Arrays
                            .stream(ports)
                            .filter(
                                    port -> {
                                        if (serverLookupService.getServerInstances().get(port) == null) {
                                            log.debug("Server with port " + port + " wasn't found.");
                                            return false;
                                        }
                                        return true;
                                    }
                            )
                            .mapToObj(x -> serverLookupService.getServerInstances().get(x))
                            .collect(Collectors.toList())
            );
        } else {
            throw new InstantiationException("Unknown launch mode, exiting.");
        }

        if (tasks.isEmpty()) {
            throw new InstantiationException("Servers list for launching is empty, exiting.");
        }

        // Submitting stage
        executorService = Executors.newFixedThreadPool(
                tasks.size(),
                r -> {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
        );

        for (RunnableServerInstance task : tasks) {
            executorService.submit(task);
            log.debug("Successfully launched an instance of " + ConversionUtils.convertToServerId(task) + ".\n");
        }

        log.debug("Successfully launched " + tasks.size() + (tasks.size() == 1 ? " instance." : " instances."));
    }

    /**
     * Small method to check, if simultaneous different executions available.
     * If {@link ExecutorService} exists - we can't make new launch and have to stop it first.
     *
     * @return true, if {@link ExecutorService} instance was created already.
     */
    public boolean isLaunchingLocked() {
        return executorService != null;
    }

    /**
     * Method to actually stop any current execution
     */
    public void stopExecution() {
        if (executorService != null) {
            try {
                for (RunnableServerInstance task : serverLookupService.getServerInstances().values()) {
                    task.stop();
                }

                executorService.shutdown();
            } catch (Exception e) {
                log.error("Unexpected exception occurred during executor service shutdown, performing force shutdown: ", e);
                executorService.shutdownNow();
            } finally {
                log.warn("Shutdown hook was called.");
                executorService = null;
            }
        }
    }

    /**
     * Small class to ease method definition
     *
     * @author Roman Khlebnov
     */
    private enum LaunchMode {
        PINPOINT, SIMULTANEOUS
    }
}
