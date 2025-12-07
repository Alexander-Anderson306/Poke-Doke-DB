package com.poke_frontend;

import java.util.List;
import com.poke_frontend.dto.request.*;
import com.poke_frontend.models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientTest {

    @Test
    void testCreateClient() {
        Client client = new Client();
        assertNotNull(client);
    }

    @Test
    @Order(1)
    void testCreateAccount() {
        Client client = new Client();
        CreateAccountRequest req = new CreateAccountRequest();
        req.username = "testuser1";
        req.password = "testpassword1";
        req.email = "testemail1@testemaildomain1.com";
        req.name = "testnamefirst testnamelast";

        try {
            boolean success = client.createAccount(req);
            //Assuming testuser1 does not already exist, we expect account creation to succeed
            assertTrue(success);
        } catch (Exception e) {
            fail("Account creation threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    @Order(2)
    void testCreateAccountDuplicate() {
        Client client = new Client();
        CreateAccountRequest req = new CreateAccountRequest();
        req.username = "testuser1";
        req.password = "testpassword1";
        req.email = "testemail1@testemaildomain1.com";
        req.name = "testnamefirst testnamelast";

        try {
            boolean success = client.createAccount(req);
            //Assuming testuser1 already exists, we expect account creation to fail
            assertFalse(success);
        } catch (Exception e) {
            fail("Account creation threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    void testLoginNoUser() {
        Client client = new Client();
        LoginRequest req = new LoginRequest();
        req.username = "notAUser";
        req.password = "testpassword1";

        try {
            boolean success = client.login(req);
            //Assuming testuser1 is not a valid user, we expect login to fail
            assertFalse(success);
        } catch (Exception e) {
            fail("Login threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test 
    void testLoginSuccess() {
        Client client = new Client();
        LoginRequest req = new LoginRequest();
        req.username = "testuser1";
        req.password = "testpassword1";

        try {
            boolean success = client.login(req);
            //Assuming testuser1 is a valid user, we expect login to succeed
            assertTrue(success);
        } catch (Exception e) {
            fail("Login threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    void testloginFailureWrongPassword() {
        Client client = new Client();
        LoginRequest req = new LoginRequest();
        req.username = "testuser1";
        req.password = "wrongpassword";

        try {
            boolean success = client.login(req);
            //Assuming testuser1 is a valid user, but password is wrong, we expect login to fail
            assertFalse(success);
        } catch (Exception e) {
            fail("Login threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    void testLoginFailureEmptyUsername() {
        Client client = new Client();
        LoginRequest req = new LoginRequest();
        req.username = "";
        req.password = "somepassword";

        try {
            boolean success = client.login(req);
            //Assuming testuser1 is a valid user, but password is wrong, we expect login to fail
            assertFalse(success);
        } catch (Exception e) {
            fail("Login threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    void testGetDBCards() {
        Client client = new Client();
        AllCardsRequest req = new AllCardsRequest();

        try {
            List<CardTypeQuant> cards = client.getDBCards(req);
            //Assuming there are cards in the DB, we expect to get a non-empty list
            assertNotNull(cards);
            assertTrue(cards.size() > 0);
            assertNotNull(cards.get(0));
        } catch (Exception e) {
            fail("Get DB Cards threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    void displayStore() {
        Client client = new Client();
        PackRequest req = new PackRequest();

        try {
            List<CardPack> packs = client.displayStore(req);
            //Assuming there are packs in the store, we expect to get a non-empty list
            assertNotNull(packs);
            assertTrue(packs.size() > 0);
            assertNotNull(packs.get(0));
        } catch (Exception e) {
            fail("Display Store threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    void testGetInventoryBadUID() {
        Client client = new Client();
        InventoryRequest req = new InventoryRequest();
        req.userId = -1; //assuming -1 is an invalid user ID

        try {
            List<CardTypeQuant> inventory = client.getInventory(req);
            //Assuming user ID -1 has no inventory, we expect to get a null list
            assertNull(inventory);
        } catch (Exception e) {
            fail("Get Inventory threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    @Order(3)
    void testGetInventoryValidUIDEmptyInventory() {
        Client client = new Client();
        InventoryRequest req = new InventoryRequest();
        req.userId = 1; //assuming 1 is a valid user ID with an empty inventory
        try {
            List<CardTypeQuant> inventory = client.getInventory(req);
            //Assuming user ID 1 has an empty inventory, we expect to get an empty list
            assertNotNull(inventory);
            assertEquals(0, inventory.size());
        } catch (Exception e) {
            fail("Get Inventory threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    @Order(4)
    void testPackPurchaseWithGoodId() {
        Client client = new Client();
        PackPurchaseRequest req = new PackPurchaseRequest();
        req.userId = 1; //assuming 1 is a valid user ID
        req.packId = 1; //assuming 1 is a valid pack ID

        try {
            List<CardTypeQuant> inventory = client.purchasePack(req);
            //We should recieve some cards back 
            assertNotNull(inventory);
            assertTrue(inventory.size() > 0);
        } catch (Exception e) {
            fail("Pack Purchase threw an exception: " + e.getMessage());
            return;
        }
    }

    @Test
    @Order(5)
    void testGetInventoryValidUID() {
        Client client = new Client();
        InventoryRequest req = new InventoryRequest();
        req.userId = 1; //assuming 1 is a valid user ID with some inventory
        try {
            List<CardTypeQuant> inventory = client.getInventory(req);
            //Assuming user ID 1 has some inventory, we expect to get a non-empty list
            assertNotNull(inventory);
            assertTrue(inventory.size() > 0);
        } catch (Exception e) {
            fail("Get Inventory threw an exception: " + e.getMessage());
            return;
        }
    }
}