package com.poke_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.poke_backend.models.*;

import com.poke_backend.dto.request.*;

public class SQLHandler {

    private Connection connection;

    public SQLHandler() throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "";   // matches your current setup

        this.connection = DriverManager.getConnection(url, user, password);
    }

    /**
	 * Checks whether the username already exists
	 * 
	 */
	
    public boolean userExists(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE user_name = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        return rs.next(); // true if we found a row
    }

 
    // Account / Authentication
    /**
     * Creates a new account in the database.
     * Throws SQLException if the username already exists.
     */
    public User createAccount(CreateAccountRequest req) throws SQLException {

        // Check if username is taken
        if (userExists(req.username)) {
            throw new SQLException("User already exists");
        }

        //Hash the password
        String hashed = Hash.hashPassword(req.password);

        //Insert user into DB
        String sql = "INSERT INTO users (user_name, password, first_name, last_name) " +
                     "VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, req.username);
        stmt.setString(2, hashed);
        stmt.setString(3, req.firstName);
        stmt.setString(4, req.lastName);

        stmt.executeUpdate();

        User user = new User();
        user.setUserName(req.username);
        user.setFirstName(req.firstName);
        user.setLastName(req.lastName);

        return user;
    }

    /**
     *attempts to log in an existing user
     *Returns null if username doesn't exist or password is wrong
     */
    public User login(LoginRequest req) throws SQLException {

        String sql =
            "SELECT id, user_name, password, first_name, last_name, picture_name " +
            "FROM users WHERE user_name = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, req.username);

        ResultSet rs = stmt.executeQuery();

        //no user with this username
        if (!rs.next()) {
            return null;
        }

        //stored hashed password
        String hashedPassword = rs.getString("password");

        //Compare passwords
        boolean ok = Hash.checkPassword(req.password, hashedPassword);
        if (!ok) {
            return null;
        }

        //Build and return user object
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("user_name"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPicture_name(rs.getString("picture_name"));

        return user;
    }

    /**
     * Logs out a user.
     */
    public void logout(LogoutRequest req) throws SQLException {
        // Intentionally left blank for this project
    }

  
    // Helper: Card types lookup
    /**
     * Loads all type names for a given card id
     */
    private List<String> getTypesForCard(int cardId) throws SQLException {
        String sql = """
                SELECT t.name
                FROM types_bridge tb
                JOIN poke_types t ON tb.type_id = t.id
                WHERE tb.card_id = ?
                """;

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cardId);

        ResultSet rs = stmt.executeQuery();
        List<String> types = new ArrayList<>();

        while (rs.next()) {
            types.add(rs.getString("name"));
        }

        return types;
    }


    // User Inventory
    /**
     * Returns the inventory for the given user as a list of CardTypeQuant
     */
    public List<CardTypeQuant> getUserInventory(InventoryRequest req) throws SQLException {

        int userId = req.userId;
        Map<Integer, CardTypeQuant> map = new LinkedHashMap<>();

        String sql = """
                SELECT c.id,
                       c.card_name,
                       c.rarity,
                       c.image_path,
                       c.thumb_path,
                       ui.quantity,
                       t.name AS type_name
                FROM user_inventory ui
                JOIN cards c ON ui.card_id = c.id
                LEFT JOIN types_bridge tb ON c.id = tb.card_id
                LEFT JOIN poke_types t ON tb.type_id = t.id
                WHERE ui.user_id = ?
                """;

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int cardId = rs.getInt("id");
            CardTypeQuant ctq = map.get(cardId);

            if (ctq == null) {
                Card card = new Card(
                        rs.getInt("id"),
                        rs.getString("card_name"),
                        rs.getString("rarity"),
                        rs.getString("image_path"),
                        rs.getString("thumb_path")
                );

                int quantity = rs.getInt("quantity");
                ctq = new CardTypeQuant(card, quantity, new ArrayList<>());
                map.put(cardId, ctq);
            }

            String typeName = rs.getString("type_name");
            if (typeName != null && !ctq.getTypes().contains(typeName)) {
                ctq.getTypes().add(typeName);
            }
        }

        return new ArrayList<>(map.values());
    }


    // Cards / main DB
    /**
     * Returns ALL cards from the main database, along with their types,
     * wrapped in CardTypeQuant object
     */
    public List<CardTypeQuant> getCards(AllCardsRequest req) throws SQLException {

        Map<Integer, CardTypeQuant> map = new LinkedHashMap<>();

        String sql = """
                SELECT c.id,
                       c.card_name,
                       c.rarity,
                       c.image_path,
                       c.thumb_path,
                       t.name AS type_name
                FROM cards c
                LEFT JOIN types_bridge tb ON c.id = tb.card_id
                LEFT JOIN poke_types t ON tb.type_id = t.id
                """;

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int cardId = rs.getInt("id");
            CardTypeQuant ctq = map.get(cardId);

            if (ctq == null) {
                Card card = new Card(
                        rs.getInt("id"),
                        rs.getString("card_name"),
                        rs.getString("rarity"),
                        rs.getString("image_path"),
                        rs.getString("thumb_path")
                );

                // quantity 0 since this is from DB, not inventory
                ctq = new CardTypeQuant(card, 0, new ArrayList<>());
                map.put(cardId, ctq);
            }

            String typeName = rs.getString("type_name");
            if (typeName != null && !ctq.getTypes().contains(typeName)) {
                ctq.getTypes().add(typeName);
            }
        }

        return new ArrayList<>(map.values());
    }
//store packs
    /**
     * Returns all available packs (no filtering)
     */
    public List<CardPack> getPacks(PackRequest req) throws SQLException {

        List<CardPack> packs = new ArrayList<>();

        String sql = "SELECT id, pack_name, price, pack_rarity FROM card_pack";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            CardPack pack = new CardPack(
                    rs.getInt("id"),
                    rs.getString("pack_name"),
                    rs.getFloat("price"),
                    rs.getString("pack_rarity")
            );

            packs.add(pack);
        }

        return packs;
    }

    /**
     * Handles purchasing a pack:
     *  - randomly selects 10 cards from card_pack_inventory, updates user_inventory, returns a list of cardtypequant 
     */
    public List<CardTypeQuant> purchasePack(PackPurchaseRequest req) throws SQLException {

        int userId = req.userId;
        Map<Integer, CardTypeQuant> pickedMap = new LinkedHashMap<>();

        // Pick 10 random card_ids from this pack
        String sql = """
                SELECT c.id,
                       c.card_name,
                       c.rarity,
                       c.image_path,
                       c.thumb_path,
                       t.name AS type_name
                FROM (
                    SELECT DISTINCT cpi.card_id
                    FROM card_pack_inventory cpi
                    WHERE cpi.pack_id = ?
                    ORDER BY RAND()
                    LIMIT 10
                ) picked
                JOIN cards c ON picked.card_id = c.id
                LEFT JOIN types_bridge tb ON c.id = tb.card_id
                LEFT JOIN poke_types t ON tb.type_id = t.id
                """;

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, req.packId);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int cardId = rs.getInt("id");
            CardTypeQuant ctq = pickedMap.get(cardId);

            if (ctq == null) {
                Card card = new Card(
                        rs.getInt("id"),
                        rs.getString("card_name"),
                        rs.getString("rarity"),
                        rs.getString("image_path"),
                        rs.getString("thumb_path")
                );

                //each pull gives you 1 copy of this card
                ctq = new CardTypeQuant(card, 1, new ArrayList<>());
                pickedMap.put(cardId, ctq);
            }

            String typeName = rs.getString("type_name");
            if (typeName != null && !ctq.getTypes().contains(typeName)) {
                ctq.getTypes().add(typeName);
            }
        }

        List<CardTypeQuant> pulled = new ArrayList<>(pickedMap.values());

        //update user_inventory for the pulled cards
        if (!pulled.isEmpty()) {
            String invSql = """
                    INSERT INTO user_inventory (user_id, card_id, quantity)
                    VALUES (?, ?, ?)
                    ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
                    """;

            PreparedStatement invStmt = connection.prepareStatement(invSql);

            for (CardTypeQuant ctq : pulled) {
                invStmt.setInt(1, userId);
                invStmt.setInt(2, ctq.getCard().getId());
                invStmt.setInt(3, ctq.getQuantity()); // usually 1
                invStmt.addBatch();
            }

            invStmt.executeBatch();
        }

        return pulled;
    }
}
