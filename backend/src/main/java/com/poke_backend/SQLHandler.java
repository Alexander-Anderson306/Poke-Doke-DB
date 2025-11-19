package com.poke_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.poke_backend.models.*;

import com.poke_backend.dto.request.*;
import com.poke_backend.dto.response.*;

public class SQLHandler {
	
	private Connection connection;
	
	public SQLHandler() throws SQLException { 
		
	}
	
//Account/ authentication
	/*
	 * creating a new account in the database
	 */
    public User createAccount(CreateAccountRequest req) throws SQLException {
    	
    	
    	return null;
    }
    
    /**
     * Attempts to log in an existing user 
     */
    public User login(LoginRequest req) throws SQLException { 
    	
    	return null;
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
    public List<UserInventory> getUserInventory(InventoryRequest req) throws SQLException { 
    	
    	return null;
}
    
    //Cards/database
    /**
     * returns cards from the main database
     */
     public List<Card> getCards(AllCardsRequest req) throws SQLException { 
    	 
    	 return null;
     }
     
     //Store/Packs
     /**
      * Returns available packs 
      */
     public List<CardPack> getPacks(PackRequest req) throws SQLException {
    	 
    	 return null;
     }
     
     /**
      * Handles purchasing a pack: 
      */
     
     public List<Card> purchasePack(PackPurchaseRequest req) throws SQLException { 
    	 
    	 return null;
     }
}