package org.nnstu4.test.structures;

import org.junit.Test;
import org.nnstu4.server.structures.Dialogue;
import org.nnstu4.server.structures.Message;
import org.nnstu4.server.structures.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class DialogueUnitTest {
    private final User validUser = new User("Username", UUID.randomUUID(), new LinkedList<>(Arrays.asList("1", "2", "3")));
    private final Message validMessage = new Message("Text", validUser, 1L);
    private final String validKey = "1";

    @Test(expected = IllegalArgumentException.class)
    public void doNullUsersTest() {
        assertEquals(null, new Dialogue(
                null,
                validKey,
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyUsersTest() {
        assertEquals(null, new Dialogue(
                new HashMap<>(),
                validKey,
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNullKeyTest() {
        HashMap<Integer, User> users = new HashMap<>();
        users.put(1, validUser);
        users.put(2, validUser);
        assertEquals(null, new Dialogue(
                users,
                null,
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyKeyTest() {
        HashMap<Integer, User> users = new HashMap<>();
        users.put(1, validUser);
        users.put(2, validUser);
        assertEquals(null, new Dialogue(
                users,
                "",
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test
    public void doNullChatHistoryTest() {
        HashMap<Integer, User> users = new HashMap<>();
        users.put(1, validUser);
        users.put(2, validUser);
        new Dialogue(
                users,
                validKey,
                null);
    }

    @Test
    public void doValidEmptyHistoryTest() {
        HashMap<Integer, User> users = new HashMap<>();
        users.put(1, validUser);
        users.put(2, validUser);
        LinkedList<Message> emptyList = new LinkedList<>();
        new Dialogue(users,
                validKey,
                emptyList);
    }

    @Test
    public void doValidNotEmptyChatHistoryTest() {
        HashMap<Integer, User> users = new HashMap<>();
        users.put(1, validUser);
        users.put(2, validUser);
        new Dialogue(users,
                validKey,
                new LinkedList<>(Arrays.asList(validMessage, validMessage)));
    }
}
