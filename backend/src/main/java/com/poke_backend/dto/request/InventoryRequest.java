package com.poke_backend.dto.request;

import java.util.List;

public class InventoryRequest {
    public int cardId;
    public int userId;
    public String cardName;
    public List<String> cardTypes;
    public String cardRarity;

    public InventoryRequest() {}
}