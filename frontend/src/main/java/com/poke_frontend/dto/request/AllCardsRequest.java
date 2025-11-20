package com.poke_frontend.dto.request;
import java.util.List;
public class AllCardsRequest {
    public String cardName;
    public List<String> cardTypes;
    public String cardRarity;

    public AllCardsRequest() {};
}