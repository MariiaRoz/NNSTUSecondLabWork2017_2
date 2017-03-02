package org.nnstu.launcher.structures;

/**
 * Small enum to determine server status
 *
 * @author Roman Khlebnov
 */
public enum ServerStatus {
    STOPPED("Stopped"),
    LAUNCHED("Launched");

    private final String value;

    ServerStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
