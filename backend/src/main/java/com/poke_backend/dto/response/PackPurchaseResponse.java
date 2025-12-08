package com.poke_backend.dto.response;

import com.poke_backend.models.CardTypeQuant;

import java.util.List;
public class PackPurchaseResponse extends BaseResponse {


    public List<CardTypeQuant> cards;

	public PackPurchaseResponse() {}

    public PackPurchaseResponse(List<CardTypeQuant> cards) {
        super(true, "Successfully purchased pack");
        this.cards = cards;
    }
}