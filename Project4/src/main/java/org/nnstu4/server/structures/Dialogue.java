package org.nnstu4.server.structures;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Class representing a chat dialogue
 *
 * @author Alexander Maslennikov
 */
public class Dialogue implements Serializable {
    private final String dialogueKey;
    private HashMap<Integer, User> users;
    private LinkedList<Message> chatHistory;

    /**
     * Constructor of the class
     * that creates already existing dialogue with chat history
     *
     * @param users       {@link Map} of pairs {@link Integer} ID : {@link User} user in the dialogue
     * @param dialogueKey {@link String}, representing dialogue key
     * @param chatHistory {@link Collection} of {@link Message}s in this dialogue. May be empty
     */
    public Dialogue(Map<Integer, User> users, String dialogueKey, Collection<Message> chatHistory) throws IllegalArgumentException {
        if (MapUtils.isEmpty(users)) {
            throw new IllegalArgumentException("initialUsers is empty");
        }

        if (StringUtils.isEmpty(dialogueKey)) {
            throw new IllegalArgumentException("dialogueKey is empty");
        }

        if (CollectionUtils.isEmpty(chatHistory)) {
            this.chatHistory = new LinkedList<>();
        } else {
            this.chatHistory = new LinkedList<>(chatHistory);
        }

        this.users = new HashMap<>(users);
        this.dialogueKey = dialogueKey;
    }

    /**
     * Adds a single {@link Message} to the {@link Dialogue#chatHistory}
     *
     * @param message {@link Message} to be added
     */
    public void addMessage(Message message) {
        Objects.requireNonNull(message);
        chatHistory.add(message);
    }

    /**
     * Adds a {@link Collection} of {@link Message}s to the {@link Dialogue#chatHistory}
     *
     * @param messages {@link Collection} of {@link Message}s to be added
     */
    public void addMultipleMessages(Collection<Message> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            throw new IllegalArgumentException("Messages is empty");
        }
        chatHistory.addAll(messages);
    }

    /**
     * Gets {@link Dialogue#dialogueKey}
     *
     * @return {@link String}, containing the key of the dialogue
     */
    public String getDialogueKey() {
        return dialogueKey;
    }

    /**
     * Gets {@link Collection} containing all messages in the dialogue
     *
     * @return {@link Collection} of all the {@link Message}s in the dialogue
     */
    public Collection<Message> getChatHistory() {
        return chatHistory;
    }

    /**
     * Sets {@link Dialogue#chatHistory} of the dialogue
     *
     * @param history {@link Collection} of the {@link Message}s to be set as a {@link Dialogue#chatHistory}
     */
    public void setChatHistory(Collection<Message> history) {
        if (CollectionUtils.isEmpty(history)) {
            throw new IllegalArgumentException("New history is empty");
        }
        chatHistory.clear();
        chatHistory.addAll(history);
    }

    /**
     * Gets a {@link Collection} of {@link User}s that are in the dialogue
     *
     * @return {@link Collection} of {@link User}s that are in the dialogue
     */
    public Map<Integer, User> getUsers() {
        return users;
    }
}
