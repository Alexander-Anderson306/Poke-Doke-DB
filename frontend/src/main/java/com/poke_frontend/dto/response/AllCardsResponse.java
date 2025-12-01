package com.poke_frontend.dto.response;

import com.poke_frontend.models.Card;
import java.util.List;

public class AllCardsResponse extends BaseResponse {
    public List<Card> cards;
    public List<String> types;
    public AllCardsResponse() {}
    public AllCardsResponse(List<Card> cards, List<String> types) {
        super(true, "Successfully retrieved cards");
        this.cards = cards;
    }
}