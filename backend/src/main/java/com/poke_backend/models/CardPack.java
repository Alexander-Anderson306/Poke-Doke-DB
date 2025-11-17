package com.poke_backend.models;

public class CardPack {
    private int id;
    private String packName;
    private float price;
    private String packRarity;

    public CardPack(int id, String packName, float price, String packRarity) {
        this.id = id;
        this.packName = packName;
        this.price = price;
        this.packRarity = packRarity;
    }

    //getters
    public int getId() {return id;}
    public String getPackName() {return packName;}
    public float getPrice() {return price;}
    public String getPackRarity() {return packRarity;}

    //setters (for jackson)
    public void setId(int id) {this.id = id;}
    public void setPackName(String packName) {this.packName = packName;}
    public void setPrice(float price) {this.price = price;}
    public void setPackRarity(String packRarity) {this.packRarity = packRarity;}
}