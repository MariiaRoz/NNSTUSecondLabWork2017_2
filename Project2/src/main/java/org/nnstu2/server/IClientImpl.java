package org.nnstu2.server;

import org.nnstu2.server.entity.Dialog;
import org.nnstu2.server.entity.Message;
import org.nnstu2.server.entity.Response;
import org.nnstu2.server.entity.SuccessResponse;

import java.util.List;

/**
 * Created by rmv52 on 28.04.2017.
 */
public class IClientImpl implements IClient {
    private IDatabase database;
    private INotification notification;

    public IClientImpl(IDatabase database, INotification notification) {
        this.database = database;
        this.notification = notification;
    }

    @Override
    public int joinRoom(String roomName, String token) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return Const.TOKEN_NOT_FOUND;
        }
        if(!database.isRoomWithoutPasswordExist(roomName)) {
            return Const.ROOM_NOT_FOUND;
        }
        database.joinRoom(roomName, login);
        return Const.SUCCESS;
    }

    @Override
    public int joinRoom(String roomName, String token, String roomPassword) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return Const.TOKEN_NOT_FOUND;
        }
        if(!database.isRoomWithoutPasswordExist(roomName)) {
            return Const.ROOM_NOT_FOUND;
        }
        if(!database.isPasswordForRoomCorrect(roomName, roomPassword)) {
            return Const.ROOM_PASSWORD_INCORRECT;
        }
        database.joinRoom(roomName, login);
        return Const.SUCCESS;
    }

    @Override
    public int register(String login, String password, String name) {
        if(database.isLoginExist(login)) {
            return Const.USER_WITH_LOGIN_ALREADY_EXISTS;
        }
        database.register(login, password, name);
        return Const.SUCCESS;
    }

    @Override
    public int addMessage(String text, String token, String roomName) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return Const.TOKEN_NOT_FOUND;
        }
        if(!database.hasRoomAccess(roomName, login)) {
            return Const.NO_ACCESS_TO_ROOM;
        }
        Message message = database.addMessage(text, login, roomName);
        notification.notifyAboutMessage(message);
        return Const.SUCCESS;
    }

    @Override
    public Response<List<Message>> getHistory(String roomName, String token, long time, int count) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return new Response<>(Const.TOKEN_NOT_FOUND);
        }
        List<Message> history = database.getHistory(roomName, time, count);
        return new SuccessResponse<>(history);
    }

    @Override
    public Response<String> login(String login, String password) {
        if(!database.isLoginExist(login))
            return new Response<>(Const.USER_WITH_SUCH_LOGIN_NOT_EXIST);

        String uuidToken = database.login(login, password);
        if(uuidToken == null) {
            return new Response<>(Const.INCORRECT_PASSWORD);
        }
        return new SuccessResponse<>(uuidToken);
    }

    @Override
    public int changeName(String newName, String token) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return Const.TOKEN_NOT_FOUND;
        }
        database.changeName(newName, login);
        return Const.SUCCESS;
    }

    @Override
    public Response<List<Dialog>> getDialogs(String token) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return new Response<>(Const.TOKEN_NOT_FOUND);
        }
        List<Dialog> dialogs = database.getDialogs(login);
        return new SuccessResponse<>(dialogs);
    }

    @Override
    public int leaveRoom(String roomName, String token) {
        String login = database.getLoginByToken(token);
        if(login == null) {
            return Const.TOKEN_NOT_FOUND;
        }
        if(!database.hasRoomAccess(roomName, login)) {
            return Const.NO_ACCESS_TO_ROOM;
        }
        database.leaveRoom(roomName, login);
        return Const.SUCCESS;
    }
}
