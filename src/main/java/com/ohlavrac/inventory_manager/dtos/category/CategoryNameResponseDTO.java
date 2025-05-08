package com.ohlavrac.inventory_manager.dtos.category;

import java.util.UUID;

public record CategoryNameResponseDTO (
    UUID id,
    String CategoryName
) {
    
}
