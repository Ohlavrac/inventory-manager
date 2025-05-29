package com.ohlavrac.inventory_manager.exceptions.hadler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ohlavrac.inventory_manager.exceptions.DeleteException;
import com.ohlavrac.inventory_manager.exceptions.ExceptionResponse;
import com.ohlavrac.inventory_manager.exceptions.OrderUpdateException;
import com.ohlavrac.inventory_manager.exceptions.ResorceNotFoundException;
import com.ohlavrac.inventory_manager.exceptions.ResourceAmountExecption;

@RestController
@ControllerAdvice
public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            exception.getMessage(),
            request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(DeleteException.class)
    public final ResponseEntity<ExceptionResponse> handleDeleteException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            exception.getMessage(),
            request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResorceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResorceNotFoundException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            exception.getMessage(),
            request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAmountExecption.class)
    public final ResponseEntity<ExceptionResponse> handleResourceAmountException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            exception.getMessage(),
            request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderUpdateException.class)
    public final ResponseEntity<ExceptionResponse> handleOrderUpdateException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            new Date(),
            exception.getMessage(),
            request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
