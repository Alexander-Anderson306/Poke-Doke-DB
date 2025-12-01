package com.poke_frontend.dto.response;

import com.poke_frontend.models.CardTypeQuant;
import java.util.List;

public class AllCardsResponse extends BaseResponse {
    public List<CardTypeQuant> cards;
    public AllCardsResponse() {}
    public AllCardsResponse(List<CardTypeQuant> cards) {
        super(true, "Successfully retrieved cards");
        this.cards = cards;
    }
}