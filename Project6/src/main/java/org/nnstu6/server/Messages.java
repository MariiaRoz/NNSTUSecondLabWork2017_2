package org.nnstu6.server;

import java.io.Serializable;
import java.sql.Timestamp;


public class Messages implements Serializable {

    private String sender;
    private String message;
    private Timestamp time;

    /**
     * @param sender  who sent the  message
     * @param message content of message
     */
    Messages(String sender, String message) {

        this.sender = sender;
        this.message = message;
        this.time = new Timestamp(System.currentTimeMillis());
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
     * Representing time when the message's been sent
     *
     * @return time
     */
    public Timestamp getTime() {
        return time;
    }

}
