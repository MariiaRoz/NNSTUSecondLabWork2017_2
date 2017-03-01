package org.nnstu.launcher;

import org.nnstu.contract.AbstractServer;
import org.nnstu.launcher.util.RunnableServerInstance;
import org.nnstu.launcher.util.ServerId;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server lookup service for easier access
 *
 * @author Roman Khlebnov
 */
public class ServerLookupService {
    private final Map<Integer, RunnableServerInstance> servers = new HashMap<Integer, RunnableServerInstance>();
    private ExecutorService executorService = null;

    public ServerLookupService(String packageName) {
        Reflections reflection = new Reflections(
                new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
        );

        for (final Class<? extends AbstractServer> server : reflection.getSubTypesOf(AbstractServer.class)) {
            RunnableServerInstance instance = null;
            int instancePort = -1;

            try {
                AbstractServer serverInstance = server.getConstructor().newInstance();
                instance = new RunnableServerInstance(serverInstance);
                instancePort = serverInstance.getServerPort();
            } catch (Exception e) {
                System.err.println("Error during attempt to launch " + server.getCanonicalName() + ": " + e.getMessage());
            }

            if (instance != null && instancePort != -1) {
                if (!servers.containsKey(instancePort)) {
                    servers.put(instancePort, instance);
                } else {
                    System.err.println("Error: server " + instance.getServerId() + " can't be created, this port is already registered.");
                }
            }
        }
    }

    /**
     * Method for simultaneous launch of all servers existing in the project
     *
     * @return {@link String} that contains all events occurred during launch phase
     */
    public String simultaneousLaunch() {
        StringBuilder result = new StringBuilder()
                .append("Trying to perform simultaneous launch of servers.\n");

        // Preventive hook
        if (servers.size() == 0) {
            return result.append("No servers found, exiting.").toString();
        }

        executorService = Executors.newFixedThreadPool(servers.size());

        // Submitting tasks for execution
        for (RunnableServerInstance task : servers.values()) {
            executorService.submit(daemonize(task));
            result.append("Successfully created instance of ").append(task.getServerId().toString()).append("\n");
        }
        result.append("Successfully submitted ").append(servers.size()).append(servers.size() == 1 ? " instance." : " instances.");

        return result.toString();
    }

    /**
     * Overloaded version of method below
     *
     * @param source {@link ServerId} of the requested server
     * @return {@link String} that contains all events occurred during launch phase
     */
    public String launchSingleInstance(ServerId source) {
        return source == null ? launchSingleInstance(Integer.MIN_VALUE) : launchSingleInstance(source.getServerPort());
    }

    /**
     * Method to launch only one selected server
     *
     * @param port {@link Integer} port of the requested server
     * @return {@link String} that contains all events occurred during launch phase
     */
    public String launchSingleInstance(int port) {
        StringBuilder result = new StringBuilder()
                .append("Trying to perform launch of a certain server.\n");

        if (port == Integer.MIN_VALUE || servers.size() == 0) {
            return result.append("Cannot find requested server.").toString();
        }

        final RunnableServerInstance runnableServerInstance = servers.get(port);

        if (runnableServerInstance == null) {
            return result.append("Cannot find server with current port.").toString();
        }

        executorService = Executors.newSingleThreadExecutor();

        // Submitting task for execution
        executorService.submit(daemonize(runnableServerInstance));
        result.append("Successfully created instance of ").append(runnableServerInstance.getServerId().toString()).append("\n");

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
                    try {
                        task.stop();
                    } catch (Exception e) {
                        System.err.println("Error occurred while stopping the task with server instance: " +
                                task.getServerId().toString() + " - " + e.getMessage());
                    }
                }

                executorService.shutdown();
            } catch (Exception e) {
                System.err.println("Unexpected exception occurred during executor service shutdown, performing force shutdown");
                executorService.shutdownNow();
            } finally {
                System.out.println("Shutdown hook was called.");
                executorService = null;
            }
        }
    }

    /**
     * Quick fix for thread overflow prevention, etc.
     *
     * @param task {@link Runnable} to be set as daemon thread
     * @return daemon {@link Thread} with task
     */
    private Thread daemonize(Runnable task) {
        if (task == null) {
            return null;
        }

        Thread daemonizedTask = new Thread(task);
        daemonizedTask.setDaemon(true);
        return daemonizedTask;
    }
}
