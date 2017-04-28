package org.nnstu2.server;

import org.nnstu2.server.entity.Dialog;
import org.nnstu2.server.entity.Response;

import java.util.List;

/**
 * Created by rmv52 on 28.04.2017.
 */
public interface IClient {
    int joinRoom(String roomName, String token);
    int joinRoom(String roomName, String token, String roomPassword);
    int register(String login, String password, String name);
    int addMessage(String message, String token, String roomName);
    Response<?> getHistory(String roomName, String token, long time, int count);
    //Получаем токен по логину и паролю
    Response<String> login(String login, String password);
    int changeName(String newName, String token);
    Response<List<Dialog>> getDialogs(String token);
    int leaveRoom(String roomName, String token);

}
