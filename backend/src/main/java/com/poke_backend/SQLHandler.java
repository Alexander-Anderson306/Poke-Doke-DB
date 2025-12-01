package com.poke_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poke_backend.models.*;

import com.poke_backend.dto.request.*;
import com.poke_backend.dto.response.*;

public class SQLHandler {
	
	private Connection connection;
	
	public SQLHandler() throws SQLException { 
		
		String url = "jdbc:mysql://localhost:3306/mydb";
		String user = "root";
		String password = ""; 
		
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
		return rs.next(); //it'll be true if the user exists

	}
	
//Account/ authentication
	/*
	 * creating a new account in the database
	 */
    public User createAccount(CreateAccountRequest req) throws SQLException {
    	
    	//checking if username is taken
    	if (userExists(req.username)) { 
    		throw new SQLException("User already exists");
    	}
    	//hashing password 
    	String hashed = Hash.hashPassword(req.password);
    	
    	//inserting user 
    	String sql = "INSERT INTO users (user_name, password, first_name, last_name) VALUES (?, ?, ?, ?)";
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
     * Attempts to log in an existing user 
     */
    public User login(LoginRequest req) throws SQLException { 
    	
    	String sql = "SELECT id, user_name, password, first_name, last_name, picture_name " + "FROM users WHERE user_name = ?";
    	
    	PreparedStatement stmt = connection.prepareStatement(sql);
    	stmt.setString(1, req.username);
    	
    	ResultSet rs = stmt.executeQuery();
    	
    	//no user with this username
    	if(!rs.next()) { 
    		return null;
    	}
    	//get password
    	String hashedPassword = rs.getString("password");
    	
    	//comparing passwords
    	boolean ok = Hash.checkPassword(req.password, hashedPassword);
    	if(!ok) { 
    		return null;
    	}
    	
    	User user = new User();
    	user.setId(rs.getInt("id"));
    	user.setUserName(rs.getString("user_name"));
    	user.setFirstName(rs.getString("first_name"));
    	user.setLastName(rs.getString("last_name"));
    	user.setPicture_name(rs.getString("picture_name"));
    	
    	return user;
    }
    /**
     *logs out a user
     */
    public void logout(LogoutRequest req) throws SQLException { 
    	
    }
    
    //USER INVENTORY 
    /**
     * Returns the inventory for the given user
     */
    public List<InventoryRequestObject> getUserInventory(InventoryRequest req) throws SQLException { 
    	
    	List<InventoryRequestObject> inventory = new ArrayList<>();
    	
    	int userId = req.userId;
    	
    	String sql = """
    			SELECT c.id,
    					c.card_name,
    					c.rarity,
    					c.image_path,
    					c.thumb_path,
    					ui.quantity
    			FROM user_inventory ui
    			JOIN cards c ON ui.card_id = c.id
    			WHERE ui.user_id = ? 
    			""";
    	
    	PreparedStatement stmt = connection.prepareStatement(sql);
    	stmt.setInt(1, userId);
    	
    	ResultSet rs = stmt.executeQuery();
    	
    	while(rs.next()) { 
    	//card model from row 
    	Card card = new Card (
    		rs.getInt("id"),
    		rs.getString("card_name"),
            rs.getString("rarity"),
            rs.getString("image_path"),
            rs.getString("thumb_path")
        );
    	
    	int quantity = rs.getInt("quantity");
    	InventoryRequestObject obj = new InventoryRequestObject(card, quantity, null);
    	
    	inventory.add(obj);
    	}
    			
    	
    	return inventory;
}
    
    //Cards/database
    /**
     * returns cards from the main database
     */
     public List<Card> getCards(AllCardsRequest req) throws SQLException { 
    	 
    	 List<Card> cards = new ArrayList<>();

    	    String sql = "SELECT id, card_name, rarity, image_path, thumb_path FROM cards";

    	    PreparedStatement stmt = connection.prepareStatement(sql);
    	    ResultSet rs = stmt.executeQuery();

    	    while (rs.next()) {
    	        Card card = new Card(
    	            rs.getInt("id"),
    	            rs.getString("card_name"),
    	            rs.getString("rarity"),
    	            rs.getString("image_path"),
    	            rs.getString("thumb_path")
    	        );

    	        cards.add(card);
    	    }

    	    return cards;
    	}
     
     //Store/Packs
     /**
      * Returns available packs 
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
      */
     
     public List<Card> purchasePack(PackPurchaseRequest req) throws SQLException { 
    	 List<Card> cards = new ArrayList<>();

    	    String sql = """
    	        SELECT c.id, c.card_name, c.rarity, c.image_path, c.thumb_path
    	        FROM card_pack_inventory cpi
    	        JOIN cards c ON cpi.card_id = c.id
    	        WHERE cpi.pack_id = ?
    	    """;

    	    PreparedStatement stmt = connection.prepareStatement(sql);
    	    stmt.setInt(1, req.packId);

    	    ResultSet rs = stmt.executeQuery();

    	    while (rs.next()) {
    	        Card card = new Card(
    	            rs.getInt("id"),
    	            rs.getString("card_name"),
    	            rs.getString("rarity"),
    	            rs.getString("image_path"),
    	            rs.getString("thumb_path")
    	        );
    	        cards.add(card);
    	    }

    	    return cards;
    	}
}