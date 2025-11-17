package com.poke_backend;

public class Types {
    private int id;
    private String name;

    //empty constructor for jackson
    public Types() {}

    public Types(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //getters (for jackson)
    public int getId() {return id;}
    public String getName() {return name;}
    //setters (for jackson)
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
}