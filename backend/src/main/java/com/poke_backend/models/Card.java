package com.poke_backend.models;

public class Card {
    private int id;
    private String cardName;
    private String rarity;
    private String imagePath;
    private String thumbPath;

    public Card(int id, String cardName, String rarity, String imagePath, String thumbPath) {
        this.id = id;
        this.cardName = cardName;
        this.rarity = rarity;
        this.imagePath = imagePath;
        this.thumbPath = thumbPath;

    }

    //getters
    public int getId() {return id;}
    public String getCardName() {return cardName;}
    public String getRarity() {return rarity;}
    public String getImagePath() {return imagePath;}
    public String getThumbPath() {return thumbPath;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setCardName(String cardName) {this.cardName = cardName;}
    public void setRarity(String rarity) {this.rarity = rarity;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}
    public void setThumbPath(String thumbPath) {this.thumbPath = thumbPath;}
}