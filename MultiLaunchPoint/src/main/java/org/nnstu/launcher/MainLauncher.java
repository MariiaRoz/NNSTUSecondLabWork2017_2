package org.nnstu.launcher;

import org.nnstu.contract.AbstractServer;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main application launch point
 *
 * @author Roman Khlebnov
 */
public class MainLauncher {
    private static final String PACKAGE_NAME = "org.nnstu.server";

    private final Reflections serversStorage;
    private final List<RunnableServerInstance> executionTasks = new ArrayList<RunnableServerInstance>();

    public MainLauncher(String packageName) {
        this.serversStorage = new Reflections(
                new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
        );
    }

    public String simultaneousLaunch() {
        StringBuilder result = new StringBuilder()
                .append("Trying to perform simultaneous launch of servers.\n");

        final Set<Class<? extends AbstractServer>> servers = serversStorage.getSubTypesOf(AbstractServer.class);

        // Preventive hook
        if (servers.size() == 0) {
            return result.append("No servers found, exiting.").toString();
        }

        final ExecutorService executorService = Executors.newFixedThreadPool(servers.size());

        // Register system shutdown hook before actual tasks submitting
        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    public void run() {
                        try {
                            for (RunnableServerInstance task : executionTasks) {
                                try {
                                    task.stop();
                                } catch (Exception e) {
                                    System.err.println("Error occurred while stopping the task with server instance: " +
                                            task.getServerInstance().getClass().getCanonicalName() + " - " + e.getMessage());
                                }
                            }

                            executorService.shutdown();
                        } catch (Exception e) {
                            System.err.println("Unexpected exception occurred during executor service shutdown, performing force shutdown");
                            executorService.shutdownNow();
                        } finally {
                            System.out.println("Shutdown hook was called.");
                        }
                    }
                }
        );

        // Creating tasks for execution
        for (final Class<? extends AbstractServer> server : servers) {
            AbstractServer serverInstance = null;

            try {
                serverInstance = server.getConstructor().newInstance();
            } catch (InstantiationException e) {
                result.append(e.getMessage());
            } catch (IllegalAccessException e) {
                result.append(e.getMessage());
            } catch (InvocationTargetException e) {
                result.append(e.getMessage());
            } catch (NoSuchMethodException e) {
                result.append(e.getMessage());
            }

            if (serverInstance == null) {
                result
                        .append("Won't able to create a task for ").append(server.getCanonicalName())
                        .append("\n");
            } else {
                result
                        .append("Successfully created a task for ").append(server.getCanonicalName())
                        .append(" on port ").append(serverInstance.getServerPort())
                        .append("\n");

                executionTasks.add(new RunnableServerInstance(serverInstance));
            }
        }

        // Submitting tasks for execution
        for (RunnableServerInstance task : executionTasks) {
            executorService.submit(task);
        }
        result.append("Successfully submitted instances: ").append(executionTasks.size());

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(new MainLauncher(PACKAGE_NAME).simultaneousLaunch());
    }
}
