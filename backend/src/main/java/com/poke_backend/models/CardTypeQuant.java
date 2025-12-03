package com.poke_backend.models;

import java.util.List;

public class CardTypeQuant {
    private Card card;
    private int quantity;
    private List<String> types;

    public CardTypeQuant(Card card, int quantity, List<String> types) {
        this.card = card;
        this.quantity = quantity;
        this.types = types;
    }

    //getters
    public Card getCard() {return card;}
    public int getQuantity() {return quantity;}
    public List<String> getTypes() {return types;}

    //setters
    public void setCard(Card card) {this.card = card;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setTypes(List<String> types) {this.types = types;}
}