package com.ohlavrac.inventory_manager.exceptions;

import java.util.Date;

public record ExceptionResponse(
    Date timestamp,
    String message,
    String details
) {
    
}
