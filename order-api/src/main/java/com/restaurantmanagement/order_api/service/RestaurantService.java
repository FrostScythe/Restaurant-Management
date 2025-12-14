package com.restaurantmanagement.order_api.service;

import com.restaurantmanagement.order_api.entity.Restaurant;
import com.restaurantmanagement.order_api.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public String registerRestaurant(@RequestBody Restaurant restaurant) {
        restaurantRepository.save(restaurant);
        return "Restaurant registered successfully";
    }

    public String getRestaurantDetails(Long restaurantId){
        Restaurant foundRestaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if(foundRestaurant != null){
            return "Restaurant Details: Name - " + foundRestaurant.getName() + ", Address - " + foundRestaurant.getAddress();
        } else {
            return "Restaurant not found";
        }
    }

    public String updateRestaurantDetails(Long restaurantId, @RequestBody Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if (existingRestaurant != null) {
            // Consider partial updates and validation
            if (updatedRestaurant.getName() != null) {
                existingRestaurant.setName(updatedRestaurant.getName());
            }
            if (updatedRestaurant.getAddress() != null) {
                existingRestaurant.setAddress(updatedRestaurant.getAddress());
            }
            if (updatedRestaurant.getPhoneNumber() != null) {
                existingRestaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
            }

            restaurantRepository.save(existingRestaurant);
            return "Restaurant details updated successfully";
        } else {
            return "Restaurant not found";
        }
    }

    public String deleteRestaurant(Long restaurantId) {
        if (restaurantRepository.existsById(restaurantId)) {
            restaurantRepository.deleteById(restaurantId);
            return "Restaurant deleted successfully";
        } else {
            return "Restaurant not found";
        }
    }
}
