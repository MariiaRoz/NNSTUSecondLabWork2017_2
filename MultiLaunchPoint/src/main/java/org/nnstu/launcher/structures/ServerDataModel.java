package org.nnstu.launcher.structures;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.nnstu.launcher.structures.immutable.ServerId;

/**
 * {@link ObservableList} information data model, implementing {@link Comparable} for better navigation via UI
 *
 * @author Roman Khlebnov
 */
@Getter
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ServerDataModel implements Comparable<ServerDataModel> {
    ServerId serverId;
    SimpleStringProperty info;
    SimpleStringProperty status;

    public ServerDataModel(ServerId info, ServerStatus status) {
        this.serverId = info;
        this.info = new SimpleStringProperty(info.toString());
        this.status = new SimpleStringProperty(status.getValue());
    }

    public String getInfo() {
        return info.get();
    }

    public SimpleStringProperty infoProperty() {
        return info;
    }

    public void setInfo(ServerId info) {
        this.info.set(info.toString());
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status.set(status.getValue());
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(ServerDataModel o) {
        return this.getServerId().getModuleName().compareTo(o.getServerId().getModuleName());
    }
}
