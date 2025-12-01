package com.poke_frontend.dto.response;

import com.poke_frontend.models.CardTypeQuant;
import java.util.List;

public class InventoryResponse extends BaseResponse {
    public List<CardTypeQuant> inventory;

    public InventoryResponse() {}

    public InventoryResponse(List<CardTypeQuant> inventory) {
        super(true, "Successfully retrieved inventory");
        this.inventory = inventory;
    }
}