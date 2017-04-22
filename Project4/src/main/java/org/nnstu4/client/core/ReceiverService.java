package org.nnstu4.client.core;

import org.nnstu4.server.structures.ReplyMessage;
import org.nnstu4.server.structures.RequestStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface that is used to ensure two-way interaction of server and client
 * Allows server to update client state when new info appears
 *
 * @author Alexander Maslennikov
 */
public interface ReceiverService extends Remote {

    /**
     * Sends a new message to the client
     *
     * @param msg New {@link ReplyMessage} to be added
     * @return {@link RequestStatus} containing info about whether the request was successful or there were errors
     */
    RequestStatus addMessage(ReplyMessage msg) throws RemoteException;
}
