package com.poke_frontend.dto.response;

import com.poke_frontend.models.InventoryRequestObject;
import java.util.List;

public class InventoryResponse {
    public List<InventoryRequestObject> inventory;

    public InventoryResponse() {}

    public InventoryResponse(List<InventoryRequestObject> inventory) {
        this.inventory = inventory;
    }
}