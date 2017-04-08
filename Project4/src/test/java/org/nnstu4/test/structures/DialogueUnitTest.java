package org.nnstu4.test.structures;

import org.junit.Test;
import org.nnstu4.server.structures.Dialogue;
import org.nnstu4.server.structures.Message;
import org.nnstu4.server.structures.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class DialogueUnitTest {
    private final User validUser = new User("Username", UUID.randomUUID(), new LinkedList<>(Arrays.asList("1", "2", "3")));
    private final Message validMessage = new Message("Text", validUser, 1L);
    private final String validKey = "1";

    @Test(expected = NullPointerException.class)
    public void doNullUsersTest() {
        assertEquals(null, new Dialogue(
                null,
                validKey,
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyUsersTest() {
        assertEquals(null, new Dialogue(
                new LinkedList<>(),
                validKey,
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = NullPointerException.class)
    public void doNullKeyTest() {
        assertEquals(null, new Dialogue(
                new LinkedList<>(Arrays.asList(validUser, validUser)),
                null,
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyKeyTest() {
        assertEquals(null, new Dialogue(
                new LinkedList<>(Arrays.asList(validUser, validUser)),
                "",
                new LinkedList<>(Arrays.asList(validMessage, validMessage))));
    }

    @Test(expected = NullPointerException.class)
    public void doNullChatHistoryTest() {
        assertEquals(null, new Dialogue(
                new LinkedList<>(Arrays.asList(validUser, validUser)),
                validKey,
                null));
    }

    @Test
    public void doValidEmptyHistoryTest() {
        LinkedList<Message> emptyList = new LinkedList<>();
        new Dialogue(new LinkedList<>(
                Arrays.asList(validUser, validUser)),
                validKey,
                emptyList);
    }

    @Test
    public void doValidNotEmptyChatHistoryTest() {
        new Dialogue(new LinkedList<>(
                Arrays.asList(validUser, validUser)),
                validKey,
                new LinkedList<>(Arrays.asList(validMessage, validMessage)));
    }
}
