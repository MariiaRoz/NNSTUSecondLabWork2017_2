package org.nnstu2.server;

import org.nnstu2.server.entity.Dialog;
import org.nnstu2.server.entity.Message;

import java.util.List;

/**
 * Created by rmv52 on 28.04.2017.
 */
public interface IDatabase {

    boolean isRoomWithoutPasswordExist(String roomName);
    boolean isRoomWithPasswordExist(String roomName);
    void joinRoom(String roomName, String login);
    void createRoom(String roomName, String password);
    void createRoom(String roomName);
    void register(String login, String password, String name);
    void changeName(String newName, String login);
    boolean isUserExist(String login, String password);
    Message addMessage(String message, String login, String roomName);
    //Получаем логин пользователя по токену
    String getLoginByToken(String uuidToken);
    List<Message> getHistory(String roomName, long time, int count);
    //Получаем токен по логину и паролю
    String login(String login, String password);
    List<Dialog> getDialogs(String login);
    void leaveRoom(String roomName, String login);
    boolean isPasswordForRoomCorrect(String roomName, String roomPassword);
    boolean isLoginExist(String login);
    boolean hasRoomAccess(String roomName, String login);
}
