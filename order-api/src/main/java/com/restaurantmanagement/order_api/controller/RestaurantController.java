package com.restaurantmanagement.order_api.controller;

import com.restaurantmanagement.order_api.entity.MenuItem;
import com.restaurantmanagement.order_api.entity.Restaurant;
import com.restaurantmanagement.order_api.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // ========== RESTAURANT CRUD OPERATIONS ==========

    // CREATE - Register new restaurant
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    // READ - Get all restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // READ - Get restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    // UPDATE - Update restaurant details
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id); // Ensure ID matches path
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    // DELETE - Delete restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // ========== MENU ITEM OPERATIONS (Sub-resources) ==========

    // GET restaurant's full menu
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItem>> getRestaurantMenu(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = restaurantService.getRestaurantMenu(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    // ADD menu item to restaurant
    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<MenuItem> addMenuItemToRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody MenuItem menuItem) {

        MenuItem createdMenuItem = restaurantService.addMenuItemToRestaurant(restaurantId, menuItem);
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }

    // GET specific menu item from restaurant
    @GetMapping("/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<MenuItem> getMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId) {

        MenuItem menuItem = restaurantService.getMenuItem(restaurantId, menuItemId);
        return ResponseEntity.ok(menuItem);
    }

    // UPDATE menu item
    @PutMapping("/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @RequestBody MenuItem menuItem) {

        menuItem.setId(menuItemId); // Ensure ID matches
        MenuItem updatedMenuItem = restaurantService.updateMenuItem(restaurantId, menuItem);
        return ResponseEntity.ok(updatedMenuItem);
    }

    // DELETE menu item from restaurant
    @DeleteMapping("/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId) {

        restaurantService.deleteMenuItem(restaurantId, menuItemId);
        return ResponseEntity.noContent().build();
    }

    // ========== RESTAURANT ORDERS ==========

    // GET restaurant's orders
    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getRestaurantOrders(@PathVariable Long id) {
        // This depends on your RestaurantService method
        // If you have: restaurantService.getRestaurantOrders(id)
        // return ResponseEntity.ok(orders);

        return ResponseEntity.ok("Orders for restaurant " + id + " (implement in service)");
    }
}