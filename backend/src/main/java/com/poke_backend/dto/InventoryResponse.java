package com.poke_backend.dto;

import com.poke_backend.models.InventoryRequestObject;
import java.util.List;

public class InventoryResponse {
    public List<InventoryRequestObject> inventory;

    public InventoryResponse() {}

    public InventoryResponse(List<InventoryRequestObject> inventory) {
        this.inventory = inventory;
    }
}