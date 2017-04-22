package org.nnstu4.server.structures;

/**
 * Enum determining status of client request to server
 *
 * @author Alexander Maslennikov
 */
public enum RequestStatus {
    SUCCESS("Success"),
    USER_NOT_FOUND_ERROR("User not found error"),
    INCORRECT_PASSWORD_ERROR("Incorrect password error"),
    CONNECTION_ERROR("Connection error");

    private final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
