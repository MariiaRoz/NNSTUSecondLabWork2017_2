package org.nnstu4.server.structures;

import java.io.Serializable;

/**
 * Represents a chat message.
 *
 * @author Alexander Maslennikov
 */
public final class Message implements Serializable {
    private final String text;
    private final User user;
    private final long time;

    /**
     * Creates a message.
     *
     * @param text {@link String}, representing text of the message
     * @param user {@link String}, representing user who sent the messageg
     * @param time {@link Long}, representing time when the message's been sent
     */
    public Message(String text, User user, long time) {
        if (text == null) {
            throw new IllegalArgumentException("Message text is NULL");
        } else if (text.length() == 0) {
            throw new IllegalArgumentException("Message text length is 0");
        }
        if (user == null) {
            throw new IllegalArgumentException("User is NULL");
        }
        if (time <= 0) {
            throw new IllegalArgumentException("Message date is incorrect");
        }

        this.text = text;
        this.user = user;
        this.time = time;
    }

    /**
     * Gets message's text
     *
     * @return {@link String}, containing message's text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets user who sent the message
     *
     * @return {@link User} who sent the message
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the date when the message's been sent
     *
     * @return {@link Long}, representing time when the message's been sent
     */
    public long getTime() {
        return time;
    }
}
