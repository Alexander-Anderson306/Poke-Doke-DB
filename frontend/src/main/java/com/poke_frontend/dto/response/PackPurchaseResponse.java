package com.poke_frontend.dto.response;

import com.poke_frontend.models.Card;
import java.util.List;
public class PackPurchaseResponse extends BaseResponse {

    public List<Card> cards;
    public PackPurchaseResponse() {}
    public PackPurchaseResponse(List<Card> cards) {
        super(true, "Successfully purchased pack");
        this.cards = cards;
    }
}
