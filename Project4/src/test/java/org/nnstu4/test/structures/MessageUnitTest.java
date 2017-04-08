package org.nnstu4.test.structures;

import org.junit.Test;
import org.nnstu4.server.structures.Message;
import org.nnstu4.server.structures.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MessageUnitTest {
    private final String validText = "This is a valid text";
    private final LinkedList<String> validKeysForUser = new LinkedList<>(Arrays.asList("1", "2", "3"));
    private final User validUser = new User("Username", UUID.randomUUID(), validKeysForUser);
    private final long validTime = 1L;

    @Test(expected = NullPointerException.class)
    public void doNullStringTest() {
        assertEquals(null, new Message(null, validUser, validTime));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyStringTest() {
        assertEquals(null, new Message("", validUser, validTime));
    }

    @Test(expected = NullPointerException.class)
    public void doNullUserTest() {
        assertEquals(null, new Message(validText, null, validTime));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doZeroTimeTest() {
        assertEquals(null, new Message(validText, validUser, 0L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNegativeTimeTest() {
        assertEquals(null, new Message(validText, validUser, -1L));
    }

    @Test
    public void doValidArgsTest() {
        new Message(validText, validUser, validTime);
    }


}
