package com.restaurantmanagement.order_api.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String resourceName,Long Id) {
        super(resourceName+" not found with id: " + Id);
    }
}