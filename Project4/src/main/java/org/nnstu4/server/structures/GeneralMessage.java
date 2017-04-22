package org.nnstu4.server.structures;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class representing message for passing info
 * from client to server and from server to db
 */
public final class GeneralMessage extends AbstractMessage {
    private final int senderId;

    public GeneralMessage(int senderId, int dialogueKey, String msgText) {
        super(dialogueKey, msgText);
        this.senderId = senderId;
    }

    public int getSenderId() {
        return senderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GeneralMessage that = (GeneralMessage) o;

        return new EqualsBuilder()
                .append(getSenderId(), that.getSenderId())
                .append(getMsgText(), that.getMsgText())
                .append(getDialogueKey(), that.getDialogueKey())
                .append(getMsgTime(), that.getMsgTime())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSenderId())
                .append(getMsgText())
                .append(getDialogueKey())
                .append(getMsgTime())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("senderId", senderId)
                .append("dialogueKey", dialogueKey)
                .append("msgTime", msgTime)
                .append("msgText", msgText)
                .toString();
    }
}
