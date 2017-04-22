package org.nnstu4.client.core;

import com.google.common.collect.Multimap;
import org.nnstu4.server.structures.ReplyMessage;
import org.nnstu4.server.structures.RequestStatus;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

/**
 * Interface that is used for managing client from GUI
 *
 * @author Alexander Maslennikov
 */
public interface ClientService {
    /**
     * Sends an authorization request to server
     *
     * @param username {@link String} containing the name of the user to authorize
     * @param password {@link String} containing user's password. Will be encrypted
     * @param isNew    {@link Boolean} flag determining whether to create a record of this user or such record already exists
     * @return {@link RequestStatus} containing info about whether the request was successful or there were errors
     */
    RequestStatus authorizeUser(String username, String password, boolean isNew) throws RemoteException;

    /**
     * Sends a request to get dialogue keys and participants for authorized user
     *
     * @return {@link Multimap} of {@link Integer}s containing dialogue keys to {@link java.util.Collection} of {@link String}s containing dialogue participants
     */
    Optional<Multimap<Integer, String>> getDialogueKeys() throws RemoteException;

    /**
     * Sends a request to get all the messages of specific dialogue
     * Should be used mostly when first accessing the dialogue after authorization
     *
     * @param dialogueKey {@link Integer} containing key of the dialogue to get messages for
     * @return {@link List} of {@link ReplyMessage}s containing dialogue's chat history
     */
    Optional<List<ReplyMessage>> getDialogueMessages(int dialogueKey) throws RemoteException;

    /**
     * Sends a request to add the message into the specific dialogue
     *
     * @param msgText     {@link String} containing the text of the message
     * @param dialogueKey {@link Integer} containing key of the dialogue to add message into
     * @return {@link RequestStatus} containing info about whether the request was successful or there were errors
     */
    RequestStatus sendMessage(String msgText, int dialogueKey) throws RemoteException;

}
