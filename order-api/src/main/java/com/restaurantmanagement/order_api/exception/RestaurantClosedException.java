package com.restaurantmanagement.order_api.exception;

import java.time.LocalTime;

public class RestaurantClosedException extends RuntimeException {

    private final String restaurantName;
    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private final LocalTime lastOrderTime;
    private final String reason;

    public RestaurantClosedException(String restaurantName,
                                     LocalTime openingTime,
                                     LocalTime closingTime,
                                     LocalTime lastOrderTime,
                                     String reason) {
        super(buildMessage(restaurantName, reason));
        this.restaurantName = restaurantName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.lastOrderTime = lastOrderTime;
        this.reason = reason;
    }

    private static String buildMessage(String restaurantName, String reason) {
        return String.format("Restaurant '%s' is currently closed. %s", restaurantName, reason);
    }

    // Getters for detailed error response
    public String getRestaurantName() {
        return restaurantName;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public LocalTime getLastOrderTime() {
        return lastOrderTime;
    }

    public String getReason() {
        return reason;
    }
}