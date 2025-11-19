package com.poke_backend.dto.response;

import com.poke_backend.models.Card;
import java.util.List;

public class AllCardsResponse extends BaseResponse {
    public List<Card> cards;
    public AllCardsResponse() {}
    public AllCardsResponse(List<Card> cards) {
        super(true, "Successfully retrieved cards");
        this.cards = cards;
    }
}