package org.nnstu2.server.entity;

/**
 * Created by rmv52 on 28.04.2017.
 */
public class Dialog {
    private String roomName;
    private Message message;

    public Dialog(String roomName, Message message) {
        this.roomName = roomName;
        this.message = message;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
