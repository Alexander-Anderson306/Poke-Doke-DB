package com.poke_backend;

public class CardPack {
    private int id;
    private String packName;
    private float price;
    private String packRarity;

    //empty constructor for jackson
    public CardPack() {}

    public CardPack(int id, String packName, float price, String packRarity) {
        this.id = id;
        this.packName = packName;
        this.price = price;
        this.packRarity = packRarity;
    }

    //getters (for jackson)
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