package org.nnstu4.server.structures;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Class representing a chat dialogue
 *
 * @author Alexander Maslennikov
 */
public class Dialogue implements Serializable {
    private final String dialogueKey;
    private LinkedList<User> users;
    private LinkedList<Message> chatHistory;

    /**
     * Constructor of the class
     * that creates already existing dialogue with chat history
     *
     * @param users       {@link Collection} of {@link User}s in the dialogue
     * @param dialogueKey {@link String}, representing dialogue key
     * @param chatHistory {@link Collection} of {@link Message}s in this dialogue. May be empty
     */
    public Dialogue(Collection<User> users, String dialogueKey, Collection<Message> chatHistory) {
        if (users == null) {
            throw new NullPointerException("initialUsers is null");
        } else if (users.isEmpty()) {
            throw new IllegalArgumentException("initialUsers is empty");
        }

        if (dialogueKey == null) {
            throw new NullPointerException("dialogueKey is null");
        } else if (dialogueKey.isEmpty()) {
            throw new IllegalArgumentException("dialogueKey is empty");
        }

        if (chatHistory == null) {
            throw new NullPointerException("chatHistory is null");
        }

        this.users = new LinkedList<>(users);
        this.dialogueKey = dialogueKey;
        if (chatHistory.isEmpty()) {
            this.chatHistory = new LinkedList<>();
        } else {
            this.chatHistory = new LinkedList<>(chatHistory);
        }
    }

    /**
     * Adds a single {@link Message} to the {@link Dialogue#chatHistory}
     *
     * @param message {@link Message} to be added
     */
    public void addMessage(Message message) {
        if (message == null) {
            throw new NullPointerException("Message is null");
        }
        chatHistory.add(message);
    }

    /**
     * Adds a {@link Collection} of {@link Message}s to the {@link Dialogue#chatHistory}
     *
     * @param messages {@link Collection} of {@link Message}s to be added
     */
    public void addMultipleMessages(Collection<Message> messages) {
        if (messages == null) {
            throw new NullPointerException("Messages is null");
        } else if (messages.isEmpty()) {
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
        chatHistory.clear();
        chatHistory.addAll(history);
    }

    /**
     * Gets a {@link Collection} of {@link User}s that are in the dialogue
     *
     * @return {@link Collection} of {@link User}s that are in the dialogue
     */
    public Collection<User> getUsers() {
        return users;
    }
}
