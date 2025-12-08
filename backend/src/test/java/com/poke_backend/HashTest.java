package com.poke_backend;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashTest {
    @Test
    void testHashFunction() {
        String input = "testpassword";
        String hash = Hash.hashPassword(input);
        assertNotNull(hash);
        assertNotEquals(input, hash);
    }

    @Test
    void testCheckPassword() {
        String input = "testpassword";
        String hash = Hash.hashPassword(input);
        assertTrue(Hash.checkPassword(input, hash));
        assertFalse(Hash.checkPassword("wrongpassword", hash));
    }
}