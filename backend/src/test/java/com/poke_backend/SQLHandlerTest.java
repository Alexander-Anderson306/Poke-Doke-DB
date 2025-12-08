package com.poke_backend;

import java.util.List;
import com.poke_backend.dto.request.*;
import com.poke_backend.models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

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
            User expectedUser = new User(-1, "testuser", "hashed_testpassword", "Test", "Testerson", "testemail@example.com");
            req.username = "testuser";
            req.password = "testpassword";
            req.email = "testemail@example.com";
            req.name = "Test Testerson";
            User added = sqlHandler.createAccount(req);
            assertEquals(expectedUser.getUserName(), added.getUserName());
            assertEquals(expectedUser.getFirstName(), added.getFirstName());
            assertEquals(expectedUser.getLastName(), added.getLastName());
            assertEquals(expectedUser.getEmail(), added.getEmail());
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testAddingDuplicateUser() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            CreateAccountRequest req = new CreateAccountRequest();
            req.username = "testuser";
            req.password = "anotherpassword";
            req.email = "testemail@example.com";
            req.name = "Test Testerson";
            sqlHandler.createAccount(req);
            fail("Expected exception not thrown");
        } catch (SQLException e) {
            assertEquals("User already exists", e.getMessage());
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testLoginUserSuccess() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            LoginRequest req = new LoginRequest();
            req.username = "testuser";
            req.password = "testpassword";
            User loggedInUser = sqlHandler.login(req);
            assertNotNull(loggedInUser);
            assertEquals("testuser", loggedInUser.getUserName());
            assertEquals("Test", loggedInUser.getFirstName());
            assertEquals("Testerson", loggedInUser.getLastName());
            assertEquals("testemail@example.com", loggedInUser.getEmail());
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testLoginUserFail() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            LoginRequest req = new LoginRequest();
            req.username = "testuser";
            req.password = "wrongpassword";
            User loggedInUser = sqlHandler.login(req);
            assertNull(loggedInUser);
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    void testLoginNonexistentUser() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            LoginRequest req = new LoginRequest();
            req.username = "nonexistentuser";
            req.password = "somepassword";
            User loggedInUser = sqlHandler.login(req);
            assertNull(loggedInUser);
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    void testGetUserInventoryEmpty() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            InventoryRequest req = new InventoryRequest();
            req.userId = 1; // Assuming user with ID 1 exists
            List<CardTypeQuant> inventory = sqlHandler.getUserInventory(req);
            assertNotNull(inventory);
            assertEquals(0, inventory.size(), "Expected empty inventory");
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    void testPurchasePack1() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            PackPurchaseRequest req = new PackPurchaseRequest();
            req.userId = 1; // Assuming user with ID 1 exists
            req.packId = 1; // Assuming pack with ID 1 exists
            List<CardTypeQuant> purchasedCards = sqlHandler.purchasePack(req);
            assertNotNull(purchasedCards);
            assertEquals(10, purchasedCards.size(), "Expected 10 cards in pack");

        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    void testPurchasePack2() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            PackPurchaseRequest req = new PackPurchaseRequest();
            req.userId = 1; // Assuming user with ID 1 exists
            req.packId = 2; // Assuming pack with ID 1 exists
            List<CardTypeQuant> purchasedCards = sqlHandler.purchasePack(req);
            assertNotNull(purchasedCards);
            assertEquals(10, purchasedCards.size(), "Expected 10 cards in pack");

        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(9)
    void testPurchasePack3() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            PackPurchaseRequest req = new PackPurchaseRequest();
            req.userId = 1; // Assuming user with ID 1 exists
            req.packId = 3; // Assuming pack with ID 1 exists
            List<CardTypeQuant> purchasedCards = sqlHandler.purchasePack(req);
            assertNotNull(purchasedCards);
            assertEquals(10, purchasedCards.size(), "Expected 10 cards in pack");

        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    @Order(10)
    void testGetUserInventoryNonEmpty() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            InventoryRequest req = new InventoryRequest();
            req.userId = 1; // Assuming user with ID 1 exists
            List<CardTypeQuant> inventory = sqlHandler.getUserInventory(req);
            assertNotNull(inventory);
            assertTrue(inventory.size() == 30, "Expected non-empty inventory with 30 cards");
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    void testGetCards() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            AllCardsRequest req = new AllCardsRequest();
            List<CardTypeQuant> cards = sqlHandler.getCards(req);
            assertNotNull(cards);
            assertTrue(cards.size() > 0, "Expected non-empty card list");
            assertNotNull(cards.get(0));
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }

    @Test
    void testGetPacks() {
        try {
            SQLHandler sqlHandler = new SQLHandler();
            PackRequest req = new PackRequest();
            List<CardPack> packs = sqlHandler.getPacks(req);
            assertNotNull(packs);
            assertTrue(packs.size() > 0, "Expected non-empty pack list");
            assertNotNull(packs.get(0));
        } catch (Exception e) {
            fail("Failed to initialize SQLHandler: " + e.getMessage());
        }
    }
}