package org.nnstu6.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

/*
It's remote interface
*/
public interface ServerPerformance extends Remote {


    /**
     * Method that allows you to receive messages
     *
     * @param message   - content of message
     * @param sender    - who send the  message
     * @param recipient - who get the message
     * @param time      -the moment when  you receive new message
     * @return - list of messages
     */
    List<Messages> getMessages(String message, String sender, String recipient, long time) throws RemoteException;

    /**
     * Method that allows you to send message
     *
     * @param message-  content of message
     * @param sender    -who send the  message
     * @param recipient - who get the message
     * @param time      -the moment when  you send  new message
     * @return -true, if the message was sent successfully
     */
    boolean sendMessage(String message, String sender, String recipient, long time) throws RemoteException;

    /**
     * Method that allows you to create a new dialogue/conversation
     *
     * @return - special key of dialog
     */
    UUID creatingNewDialog() throws RemoteException;


    /**
     * Method that allows checking login and password
     *
     * @param login    - login of user
     * @param password - password of user
     * @return true, if check-in was successful
     */
    boolean authorization(String login, UUID password) throws RemoteException;

}
