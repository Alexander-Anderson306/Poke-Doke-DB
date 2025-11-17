package com.poke_backend;

public class UserInventory {
    private int userId;
    private int cardId;
    private int quantity;

    //empty constructor for jackson
    public UserInventory() {}

    public UserInventory(int userId, int cardId, int quantity) {
        this.userId = userId;
        this.cardId = cardId;
        this.quantity = quantity;
    }

    //getters (for jackson)
    public int getUserId() {return userId;}
    public int getCardId() {return cardId;}
    public int getQuantity() {return quantity;}

    //setters (for jackson)
    public void setUserId(int userId) {this.userId = userId;}
    public void setCardId(int cardId) {this.cardId = cardId;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}