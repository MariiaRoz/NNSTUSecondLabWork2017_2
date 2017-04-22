package org.nnstu6.server;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.UUID;

public class Authentication implements Serializable {
    private final String login;
    private final UUID password;

    Authentication(String login, UUID password) {
        if (StringUtils.isEmpty(login)) {
            throw new IllegalArgumentException("Login is null");
        }
        if (password == null) {
            throw new NullPointerException("Password is null!");
        }
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