package org.nnstu.launcher.util;

/**
 * Small POJO for server identification
 *
 * @author Roman Khlebnov
 */
public class ServerId {
    private final int serverPort;
    private final String className;
    private final String moduleName;

    public ServerId(int serverPort, String className, String moduleName) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServerId serverId = (ServerId) o;

        return serverPort == serverId.serverPort;
    }

    @Override
    public int hashCode() {
        int result = serverPort;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + moduleName + "] " + className + ":" + serverPort;
    }
}
