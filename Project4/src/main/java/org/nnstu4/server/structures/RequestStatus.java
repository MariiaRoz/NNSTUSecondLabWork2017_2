package org.nnstu4.server.structures;

/**
 * Enum determining status of client request to server
 *
 * @author Alexander Maslennikov
 */
public enum RequestStatus {
    SUCCESS("Success"),
    ILLEGAL_ARGUMENT_ERROR("Illegal argument error"),
    NOT_FOUND_ERROR("Not found error"),
    CONNECTION_ERROR("Connection error"),
    ACCESS_ERROR("Access error"),
    UNDEFINED_ERROR("Undefined error");

    private final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
