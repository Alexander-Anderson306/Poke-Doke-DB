package com.poke_frontend.models;

public class User {
    private int id;
    private String userName;
    //make sure to store this in a hash
    private String passowrd;
    private String name;
    private String pictureName;
    
    //for jackson
    public User() {}

    public User(int id, String userName, String passowrd, String name, String picture_name) {
        this.id = id;
        this.userName = userName;
        this.passowrd = passowrd;
        this.name = name;
        this.pictureName = picture_name;
    }

    //getters
    public int getId() {return id;}
    public String getUserName() {return userName;}
    public String getPassowrd() {return passowrd;}
    public String getName() {return name;}
    public String getPicture_name() {return pictureName;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setPassowrd(String passowrd) {this.passowrd = passowrd;}
    public void setName(String name) {this.name = name;}
    public void setPicture_name(String picture_name) {this.pictureName = picture_name;}
}