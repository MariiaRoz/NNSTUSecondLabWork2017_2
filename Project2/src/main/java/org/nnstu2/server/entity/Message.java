package org.nnstu2.server.entity;

/**
 * Created by rmv52 on 28.04.2017.
 */
public class Message {
    private int id;
    private String text;
    private String name;
    private long date;
    private String roomName;

    public Message(int id, String text, String name, long date, String roomName) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.date = date;
        this.roomName = roomName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
