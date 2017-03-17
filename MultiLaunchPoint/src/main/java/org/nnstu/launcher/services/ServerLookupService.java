package org.nnstu.launcher.services;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Server lookup service for easier access
 *
 * @author Roman Khlebnov
 */
public class ServerLookupService {
    private static final Logger log = Logger.getLogger(ServerLookupService.class);

    private final Map<Integer, RunnableServerInstance> servers = new HashMap<>();

    /**
     * Constructor to setup lookup service for determined packageName
     *
     * @param packageName package to look {@link AbstractServer} for
     */
    private ServerLookupService(String packageName) {
        Reflections reflection = new Reflections(
                new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
        );

        servers.putAll(ConversionUtils.convertToRunnableInstances(reflection.getSubTypesOf(AbstractServer.class)));
        log.debug("ServerLookupService have found " + (MapUtils.isEmpty(servers) ? "0" : servers.size()) + " servers.");
    }

    /**
     * Factory method to get new instance
     *
     * @param packageName package to look {@link AbstractServer} for
     * @return ready to use {@link ServerLookupService}
     */
    public static ServerLookupService forPackage(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            log.error("Cannot launch ServerLookupService for defined package.");
            return null;
        }

        return new ServerLookupService(packageName);
    }

    /**
     * Method to get all servers and their ports
     *
     * @return {@link Map} with server instances
     */
    public Map<Integer, RunnableServerInstance> getServerInstances() {
        return servers;
    }
}
