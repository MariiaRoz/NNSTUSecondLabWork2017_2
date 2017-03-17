package org.nnstu.launcher.structures.immutable;

import java.util.Objects;

/**
 * Small immutable POJO for server identification
 *
 * @author Roman Khlebnov
 */
public final class ServerId {
    private static final ServerId EMPTY_INSTANCE = new ServerId(-1, "", "");

    private final int serverPort;
    private final String className;
    private final String moduleName;

    public ServerId(int serverPort, String className, String moduleName) {
        Objects.requireNonNull(className);
        Objects.requireNonNull(moduleName);

        this.serverPort = serverPort;
        this.className = className;
        this.moduleName = moduleName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getClassName() {
        return className;
    }

    public String getModuleName() {
        return moduleName;
    }

    public static ServerId emptyServerId() {
        return EMPTY_INSTANCE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServerId serverId = (ServerId) o;

        return getServerPort() == serverId.getServerPort() &&
                getClassName().equals(serverId.getClassName()) &&
                getModuleName().equals(serverId.getModuleName());
    }

    @Override
    public int hashCode() {
        int result = getServerPort();
        result = 31 * result + getClassName().hashCode();
        result = 31 * result + getModuleName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" + getModuleName() + "] " + getClassName() + ":" + getServerPort();
    }
}
