package com.poke_frontend.dto.response;

import com.poke_frontend.models.CardPack;
import java.util.List;

public class PackResponse extends BaseResponse {
    public List<CardPack> packs;
    public PackResponse() {}
    public PackResponse(List<CardPack> packs) {
        super(true, "Successfully retrieved packs");
        this.packs = packs;
    }
}