package org.nnstu.launcher.services;

import org.apache.log4j.Logger;
import org.nnstu.contract.AbstractServer;
import org.nnstu.launcher.structures.immutable.RunnableServerInstance;
import org.nnstu.launcher.util.ConversionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Server lookup service for easier access
 *
 * @author Roman Khlebnov
 */
public class ServerLookupService {
    private static final Logger log = Logger.getLogger(ServerLookupService.class);

    private final Map<Integer, RunnableServerInstance> servers = new HashMap<>();
    private ExecutorService executorService = null;

    /**
     * Constructor to setup lookup service for determined packageName
     *
     * @param packageName package to look {@link AbstractServer} for
     */
    public ServerLookupService(String packageName) {
        Reflections reflection = new Reflections(
                new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
        );

        servers.putAll(ConversionUtils.convertToRunnableInstances(reflection.getSubTypesOf(AbstractServer.class)));
    }

    /**
     * Method-decorator for simultaneous launch of all servers existing in the project
     *
     * @return {@link String} that contains all events occurred during launch phase
     */
    public String simultaneousLaunch() throws InstantiationException {
        return launch(LaunchMode.SIMULTANEOUS);
    }

    /**
     * Method-decorator to launch several selected servers
     *
     * @param ports {@link Integer} ports of the requested servers
     * @return {@link String} that contains all events occurred during launch phase
     */
    public String pinpontLaunch(int... ports) throws InstantiationException {
        return launch(LaunchMode.PINPOINT, ports);
    }

    /**
     * Hidden method to perform most heavy lifting tasks and verifying.
     *
     * @param mode  - {@link LaunchMode}
     * @param ports - {@link Integer} ports of selected servers
     * @return {@link String} that contains all events occurred during launch phase
     */
    private String launch(LaunchMode mode, int... ports) throws InstantiationException {
        StringBuilder result = new StringBuilder().append("Trying to perform simultaneous launch of servers.\n");

        // Different checks and tasks preparing
        if (servers.isEmpty()) {
            throw new InstantiationException("Servers list is empty, cannot perform launch.");
        }

        Set<RunnableServerInstance> tasks = new HashSet<>();

        if (mode.equals(LaunchMode.SIMULTANEOUS)) {
            tasks.addAll(servers.values());
        } else if (mode.equals(LaunchMode.PINPOINT)) {
            if (ports.length == 0) {
                throw new InstantiationException("Cannot perform point launch, ports were not provided.");
            }

            tasks.addAll(
                    Arrays
                            .stream(ports)
                            .filter(
                                    port -> {
                                        if (servers.get(port) == null) {
                                            log.debug("Server with port " + port + " wasn't found.");
                                            return false;
                                        }
                                        return true;
                                    }
                            )
                            .mapToObj(servers::get)
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
            result.append("Successfully launched an instance of ").append(ConversionUtils.convertToServerId(task)).append("\n");
        }

        result.append("Successfully launched ").append(tasks.size()).append(tasks.size() == 1 ? " instance." : " instances.");

        return result.toString();
    }

    /**
     * Method to get all servers
     *
     * @return {@link Collection} with server instances
     */
    public Collection<RunnableServerInstance> getAllServerInstances() {
        return servers.values();
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
                for (RunnableServerInstance task : servers.values()) {
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
