package com.poke_backend.dto.response;

import com.poke_backend.models.CardPack;
import java.util.List;

public class PackResponse extends BaseResponse {
    public List<CardPack> packs;
    public PackResponse() {}
    public PackResponse(List<CardPack> packs) {
        super(true, "Successfully retrieved packs");
        this.packs = packs;
    }
}