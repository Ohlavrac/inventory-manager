package com.ohlavrac.inventory_manager.dtos.products;

import java.util.Set;
import java.util.UUID;

public record ProductRequestDTO(
    String productName,
    String brand,
    int amount,
    double price,
    Set<UUID> categoriesIds
) {
}
