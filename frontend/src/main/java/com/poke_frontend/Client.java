package com.poke_frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poke_frontend.dto.request.*;
import com.poke_frontend.dto.response.*;
import com.poke_frontend.models.*;

import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;

public class Client {
    private final HttpClient http;
    private final ObjectMapper mapper;

    private String baseURL ="http://localhost:8080";

    private int userId;
    private String username;

    public Client() {
        this.http = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    
    /**
     * Attempts to log in an existing user 
     * @param req request holding the username and password
     * @return true if the login was successful, false otherwise
     */
    public boolean login(LoginRequest req) throws Exception {
        this.username = req.username;

        //make json from request
        String json = mapper.writeValueAsString(req);

        //make request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/login"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        //send request and get response
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        //parse response as base response to make sure it was successful
        BaseResponse base = mapper.readValue(response.body(), LoginResponse.class);

        if(!base.success) {
            IO.print(base.message);
            return false;
        }

        //now parse it as a login response
        LoginResponse loginResponse = mapper.readValue(response.body(), LoginResponse.class);

        //set userId
        this.userId = loginResponse.userId;
        return true;
    }

    /**
     * Attempts to create a new account for the user
     * @param req the request from the user
     * @return true if the account was created successfully, false otherwise
     * @throws Exception
     */
    public boolean createAccount(CreateAccountRequest req) throws Exception {
        //make json from request
        String json = mapper.writeValueAsString(req);

        //make request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/create-account"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        //send request and get response
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        //parse response as base response to make sure it was successful
        BaseResponse base = mapper.readValue(response.body(), BaseResponse.class);

        if(!base.success) {
            IO.print(base.message);
            return false;
        }
        
        return true;
    }

    /**
     * Attempts to retrieve the inventory for the given user
     * @param req the request from the user
     * @return a list of inventory objects if the request was successful, null otherwise
     * @throws Exception
     */
    public List<CardTypeQuant> getInventory(InventoryRequest req) throws Exception {
        String json = mapper.writeValueAsString(req);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/inventory"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        BaseResponse base = mapper.readValue(response.body(), InventoryResponse.class);

        if(!base.success) {
            IO.print(base.message);
            return null;
        }

        InventoryResponse dbResponse = mapper.readValue(response.body(), InventoryResponse.class);
        return dbResponse.inventory;
    }


    public List<CardTypeQuant> getDBCards(AllCardsRequest req) throws Exception{
        String json = mapper.writeValueAsString(req);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/cards"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        BaseResponse base = mapper.readValue(response.body(), AllCardsResponse.class);

        if(!base.success) {
            IO.print(base.message);
            return null;
        }

        AllCardsResponse dbResponse = mapper.readValue(response.body(), AllCardsResponse.class);
        return dbResponse.cards;
    }

    /**
     * Returns available packs in the store
     * @param req the request from the user
     * @return a list of packs
     * @throws Exception
     * 
     */
    public List<CardPack> displayStore(PackRequest req) throws Exception{
        String json = mapper.writeValueAsString(req);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/store"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        BaseResponse base = mapper.readValue(response.body(), PackResponse.class);

        if(!base.success) {
            IO.print(base.message);
            return null;
        }

        PackResponse storeResponse = mapper.readValue(response.body(), PackResponse.class);
        return storeResponse.packs;
    }

    /**
     * Attempts to purchase a a available packs in the store
     * @param req the request from the user
     * @return a list of packs
     * @throws Exception
    */    
    public List<CardTypeQuant> purchasePack(PackPurchaseRequest req) throws Exception{
        String json = mapper.writeValueAsString(req);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/purchase"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        BaseResponse base = mapper.readValue(response.body(), PackPurchaseResponse.class);

        if(!base.success) {
            IO.print(base.message);
            return null;
        }

        PackPurchaseResponse purchaseResponse = mapper.readValue(response.body(), PackPurchaseResponse.class);
        return purchaseResponse.cards;
    }
}