package org.nnstu.launcher.structures.immutable;

import lombok.Value;

/**
 * Small immutable POJO for server identification
 *
 * @author Roman Khlebnov
 */
@Value
public class ServerId {
    private static final ServerId EMPTY_INSTANCE = new ServerId(-1, "", "");

    int serverPort;
    String className;
    String moduleName;

    public static ServerId emptyServerId() {
        return EMPTY_INSTANCE;
    }

    @Override
    public String toString() {
        return "[" + getModuleName() + "] " + getClassName() + ":" + getServerPort();
    }
}
