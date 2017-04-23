package org.nnstu6.server;

import java.io.Serializable;
import java.util.UUID;

public class Authentication implements Serializable {
    private final String login;
    private final UUID password;

    Authentication(String login, UUID password) {

        this.login = login;
        this.password = password;
    }

    /**
     * Gets user's name
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets user's password
     *
     * @return password
     */
    public UUID getPassword() {
        return password;
    }
}