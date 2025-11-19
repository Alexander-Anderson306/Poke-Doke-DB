package com.poke_frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poke_frontend.dto.LoginRequest;

import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.ProviderMismatchException;

public class Client {
    private final HttpClient http;
    private final ObjectMapper mapper;

    private String baseURL = "https://changeme.com";

    private int userId;
    private String username;

    public Client() {
        this.http = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    //login
    public boolean login(String username, String password) throws Exception {
        this.username = username;

        LoginRequest req = new LoginRequest();
        req.username = username;
        req.password = password;

        //make json from request
        String json = mapper.writeValueAsString(req);

        //make request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + "/login"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        //get response
        return false;
    }
}