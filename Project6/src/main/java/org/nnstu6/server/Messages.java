package org.nnstu6.server;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


public class Messages implements Serializable {

    private String sender;
    private String message;
    private long time;

    /**
     * @param sender  who sent the  message
     * @param message content of message
     * @param time    representing moment, when the message's been sent
     */
    Messages(long time, String sender, String message) {

        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Message is empty!");
        }
        if (time <= 0) {
            throw new IllegalArgumentException("Time is not correct!");
        }
        if (StringUtils.isEmpty(sender)) {
            throw new IllegalArgumentException("Sender is empty!");

        }

        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    /**
     * Gets who sent the message
     *
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets message's text
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * representing time when the message's been sent
     *
     * @return time
     */
    public long getTime() {
        return time;
    }

}
