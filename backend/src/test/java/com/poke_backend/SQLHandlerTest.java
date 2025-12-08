package com.poke_backend;

import java.util.List;
import com.poke_backend.dto.request.*;
import com.poke_backend.models.*;
import com.poke_backend.SQLHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SQLHandlerTest {

    @BeforeAll
    static void informTestRequirments() {
        IO.println("\n\n==================== IMPORTANT ====================\n");
        IO.println("These test require a fresh empty database to function correctly." +
        " Ensure the database is reset before running these tests.");
        IO.println("\n==================================================\n");
    }

    @Test
    @Order(1)
    void testUserAddingUser() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            CreateAccountRequest req = new CreateAccountRequest();
            req.username = "testuser";
            req.password = "testpassword";
            req.email = "testemail@example.com";
            boolean added = sqlHandler.createAccount();
            assertTrue(added, "User should be added successfully.");
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }
}