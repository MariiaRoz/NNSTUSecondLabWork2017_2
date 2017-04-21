package org.nnstu4.server.structures;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/**
 * Class representing a user
 * Provides basic functionality associated with user
 *
 * @author Alexander Maslennikov
 */
public final class User implements Serializable {
    private final String username;
    private final UUID password;
    private final LinkedList<String> dialogueKeys;
    private final transient Logger logger = Logger.getLogger(getClass());

    /**
     * Constructor of the class
     *
     * @param username     {@link String}, representing name of the user
     * @param password     {@link UUID}, representing hashed user password
     * @param dialogueKeys {@link Collection} of {@link String} representing keys of dialogues that the user is in
     */
    public User(String username, UUID password, Collection<String> dialogueKeys) throws IllegalArgumentException {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException(UserErrorMessage.EMPTY_USERNAME.getMessage());
        }

        Objects.requireNonNull(password, "Password is null");

        if (CollectionUtils.isEmpty(dialogueKeys)) {
            throw new IllegalArgumentException(UserErrorMessage.EMPTY_DIALOGUE_KEYS.getMessage());
        }

        this.username = username;
        this.password = password;
        this.dialogueKeys = new LinkedList<>(dialogueKeys);
    }

    /**
     * Gets a {@link Collection} of keys of dialogues that the user is in
     *
     * @return Keys of dialogues that the user is in
     */
    public Collection<String> getDialogueKeys() {
        return dialogueKeys;
    }

    /**
     * Creates new {@link User} object, that is copying this object and sets new {@link User#dialogueKeys}
     *
     * @param dialogueKeys {@link Collection} of {@link String} with new {@link User#dialogueKeys}
     * @return {@link User}, that is copying this object with new {@link User#dialogueKeys}
     */
    public User setDialogueKeys(Collection<String> dialogueKeys) {
        if (CollectionUtils.isNotEmpty(dialogueKeys)) {
            return new User(username, password, dialogueKeys);
        } else {
            logger.warn("dialogueKeys is empty");
            return this;
        }
    }

    /**
     * Gets user's name
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Creates new {@link User} object, that is copying this object and sets new {@link User#dialogueKeys}
     *
     * @param username {@link String}, representing new {@link User#username}
     * @return {@link User}, that is copying this object with new {@link User#dialogueKeys}
     */
    public User setUsername(String username) {
        if (StringUtils.isNotEmpty(username)) {
            return new User(username, password, dialogueKeys);
        } else {
            logger.warn("username is empty");
            return this;
        }
    }

    /**
     * Gets user's password
     *
     * @return User's password
     */
    public UUID getPassword() {
        return password;
    }

    /**
     * Creates new {@link User} object, that is copying this object and sets new {@link User#password}
     *
     * @param password {@link UUID}, representing new {@link User#password}
     * @return {@link User}, that is copying this object with new {@link User#password}
     */
    public User setPassword(UUID password) {
        Objects.requireNonNull(password, "Password is null");

        return new User(username, password, dialogueKeys);
    }

    /**
     * Enum determining exception messages that may be raised in {@link User} class
     */
    public enum UserErrorMessage {
        EMPTY_USERNAME("Username is empty"),
        EMPTY_DIALOGUE_KEYS("DialogueKeys is empty");

        private final String message;

        UserErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
