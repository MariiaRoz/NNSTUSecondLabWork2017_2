package org.nnstu4.test.structures;

import org.junit.Test;
import org.nnstu4.server.structures.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserUnitTest {
    private final String validUsername = "Username";
    private final UUID validPassword = UUID.randomUUID();
    private final LinkedList<String> validKeys = new LinkedList<>(Arrays.asList("1", "2", "3"));
    private final User validInstance = new User(validUsername, validPassword, validKeys);

    @Test(expected = NullPointerException.class)
    public void doNullStringTest() {
        assertEquals(null, new User(null, validPassword, validKeys));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyStringTest() {
        assertEquals(null, new User("", validPassword, validKeys));
    }

    @Test(expected = NullPointerException.class)
    public void doNullPasswordTest() {
        assertEquals(null, new User(validUsername, null, validKeys));
    }

    @Test(expected = NullPointerException.class)
    public void doNullDialogueKeysTest() {
        assertEquals(null, new User(validUsername, validPassword, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doEmptyDialogueKeysTest() {
        assertEquals(null, new User(validUsername, validPassword, new LinkedList<>()));
    }

    @Test
    public void doValidArgsTest() {
        new User(validUsername, validPassword, validKeys);
    }
}
