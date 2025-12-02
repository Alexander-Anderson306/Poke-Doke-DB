package com.poke_backend.dto.response;

import com.poke_backend.models.CardTypeQuant;
import java.util.List;

public class InventoryResponse extends BaseResponse {
    public List<CardTypeQuant> inventory;

    public InventoryResponse() {}

    public InventoryResponse(List<CardTypeQuant> inventory) {
        super(true, "Successfully retrieved inventory");
        this.inventory = inventory;
    }
}