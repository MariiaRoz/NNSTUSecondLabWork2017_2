package org.nnstu2.server;

import org.nnstu2.server.entity.Message;

/**
 * Created by rmv52 on 28.04.2017.
 */
public interface INotification {
    void notifyAboutMessage(Message message);
//    void notifyAboutClientLeaveFromRoom(String roomName, String login);
}
