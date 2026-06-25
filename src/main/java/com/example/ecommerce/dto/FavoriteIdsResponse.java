package com.example.ecommerce.dto;

import java.util.Set;

public record FavoriteIdsResponse(
        Set<Integer> ids
) {
}
