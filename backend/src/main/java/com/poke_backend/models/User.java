package com.poke_backend.models;

public class User {
    private int id;
    private String userName;
    //make sure to store this in a hash
    private String password;
    private String firstName;
    private String lastName;
   
    
    public User() { }

    public User(int id, String userName, String password, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
      
    }

    //getters
    public int getId() {return id;}
    public String getUserName() {return userName;}
    public String getPassowrd() {return password;}
    public String getFirstName() {return firstName; }
    public String getLastName() {return lastName;}
    
    //setters
    public void setId(int id) {this.id = id;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setPassowrd(String password) {this.password = password;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
   
}