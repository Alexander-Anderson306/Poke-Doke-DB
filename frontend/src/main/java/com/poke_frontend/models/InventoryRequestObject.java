package com.poke_frontend.models;

import com.poke_frontend.models.Card;

public class InventoryRequestObject {
    private Card card;
    private int quantity;

    public InventoryRequestObject(Card card, int quantity) {
        this.card = card;
        this.quantity = quantity;
    }

    //getters
    public Card getCard() {return card;}
    public int getQuantity() {return quantity;}

    //setters
    public void setCard(Card card) {this.card = card;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}