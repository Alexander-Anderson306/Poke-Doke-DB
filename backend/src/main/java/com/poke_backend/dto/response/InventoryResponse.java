package com.poke_backend.dto.response;

import com.poke_backend.models.InventoryRequestObject;
import java.util.List;

public class InventoryResponse extends BaseResponse {
    public List<InventoryRequestObject> inventory;

    public InventoryResponse() {}

    public InventoryResponse(List<InventoryRequestObject> inventory) {
        super(true, "Successfully retrieved inventory");
        this.inventory = inventory;
    }
}