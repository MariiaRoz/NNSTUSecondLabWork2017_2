package org.nnstu4.server.structures;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Abstract class representing message
 */
public abstract class AbstractMessage implements Serializable {
    protected final String msgText;
    protected final int dialogueKey;
    protected final Timestamp msgTime;

    public AbstractMessage(int dialogueKey, String msgText) {
        this.msgText = msgText;
        this.dialogueKey = dialogueKey;
        this.msgTime = new Timestamp(System.currentTimeMillis());
    }

    public String getMsgText() {
        return msgText;
    }

    public int getDialogueKey() {
        return dialogueKey;
    }

    public Timestamp getMsgTime() {
        return msgTime;
    }
}
