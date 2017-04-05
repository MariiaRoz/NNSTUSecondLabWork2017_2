package org.nnstu6.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

     /*
    It's interface which using client and implements of server,remote service in RMI
     */
public interface ServerPerformance extends Remote {


    /**
     *A method that allows you to receive messages and save them in a list
     *
     * @param message - content of message
     * @param sender - who send the  message
     * @param recipient - who get the message
     * @param time -the moment when  you receive a new message
     * @return - list of messages
     */
    List getData(String message, String sender,String recipient, int time) throws RemoteException;

    /**
     *A method that allows you to send messages and issue an error if failed to implement this
     *
     * @param message- content of message
     * @param sender -who send the  message
     * @param recipient- who get the message
     * @param time -the moment when  you receive a new message
     * @return -true, if the message is sent, else false
     */
    boolean sendData(String message, String sender, String recipient,int time) throws RemoteException;

    /**
     *A method that allows the client to create a new dialogue/conversation
     *
     * @param key -unique identifier for history of BD
     * @return - true, if a new conversation is created, otherwise false
     */
    boolean creatingNewDialog(String key) throws RemoteException;

    /**
     * Method that validates the user-entered login and password
     *
     * @param login - login of user
     * @param password - password of user
     * @return 2, if failed to login
     * 1, login and password are correct
     * 3, if failed in password
     * 0, unexpected error
     */
    int authorization(String login, String password) throws RemoteException;

    /**
     *A method that allows a new user to register
     *
     * @param login - login of user
     * @param password - password of user
     * @return true, if check-in was successful
     * false,if failed to register
     */
    boolean registration(String login, String password) throws RemoteException;

}
