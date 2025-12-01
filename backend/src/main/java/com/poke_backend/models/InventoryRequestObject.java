package com.poke_backend.models;

import com.poke_backend.models.Card;
import java.util.List;

public class InventoryRequestObject {
    private Card card;
    private int quantity;
    private List<String> types;

    public InventoryRequestObject(Card card, int quantity, List<String> types) {
        this.card = card;
        this.quantity = quantity;
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