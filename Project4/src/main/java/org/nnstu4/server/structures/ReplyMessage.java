package org.nnstu4.server.structures;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class representing a message for passing info
 * <p>
 * from server to client
 */
public final class ReplyMessage extends AbstractMessage {
    private final String senderName;

    public ReplyMessage(int dialogueKey, String msgText, String senderName) {
        super(dialogueKey, msgText);
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReplyMessage that = (ReplyMessage) o;

        return new EqualsBuilder()
                .append(getSenderName(), that.getSenderName())
                .append(getMsgText(), that.getMsgText())
                .append(getDialogueKey(), that.getDialogueKey())
                .append(getMsgTime(), that.getMsgTime())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSenderName())
                .append(getMsgText())
                .append(getDialogueKey())
                .append(getMsgTime())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("senderName", senderName)
                .append("dialogueKey", dialogueKey)
                .append("msgTime", msgTime)
                .append("msgText", msgText)
                .toString();
    }
}
