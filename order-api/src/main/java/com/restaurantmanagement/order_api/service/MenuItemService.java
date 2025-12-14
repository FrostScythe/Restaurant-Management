import com.restaurantmanagement.order_api.entity.MenuItem;
import com.restaurantmanagement.order_api.entity.Restaurant;
import com.restaurantmanagement.order_api.repository.MenuItemRepository;
import com.restaurantmanagement.order_api.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Create menu item FOR a specific restaurant
    public String createMenuItem(Long restaurantId, MenuItem menuItem) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);

        if (restaurantOpt.isEmpty()) {
            return "Error: Restaurant with ID " + restaurantId + " not found";
        }

        menuItem.setRestaurant(restaurantOpt.get());
        menuItemRepository.save(menuItem);
        return "Menu item created successfully for restaurant ID " + restaurantId;
    }

    // Get menu item WITH restaurant validation
    public ResponseEntity<String> getMenuItem(Long restaurantId, Long menuItemId) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(menuItemId);

        if (menuItemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Menu item with ID " + menuItemId + " not found");
        }

        MenuItem menuItem = menuItemOpt.get();

        // Verify it belongs to the specified restaurant
        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Menu item with ID " + menuItemId +
                            " does not belong to restaurant ID " + restaurantId);
        }

        String response = String.format(
                "Menu Item Details: Name - %s, Description - %s, Price - %.2f",
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice()
        );

        return ResponseEntity.ok(response);
    }

    public String updateMenuItem(Long restaurantId, Long menuItemId, MenuItem updatedItem) {
        // First check if menu item exists
        Optional<MenuItem> existingItemOpt = menuItemRepository.findById(menuItemId);

        if (existingItemOpt.isEmpty()) {
            return "Error: Menu item with ID " + menuItemId + " not found";
        }

        MenuItem existingItem = existingItemOpt.get();

        // Verify it belongs to the specified restaurant
        if (!existingItem.getRestaurant().getId().equals(restaurantId)) {
            return "Error: Menu item with ID " + menuItemId +
                    " does not belong to restaurant ID " + restaurantId;
        }

        // Update fields
        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());

        menuItemRepository.save(existingItem);
        return "Menu item '" + existingItem.getName() + "' updated successfully";
    }

    public String deleteMenuItem(Long restaurantId, Long menuItemId) {
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(menuItemId);

        if (menuItemOpt.isEmpty()) {
            return "Error: Menu item with ID " + menuItemId + " not found";
        }

        MenuItem menuItem = menuItemOpt.get();

        // Verify it belongs to the specified restaurant
        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            return "Error: Menu item with ID " + menuItemId +
                    " does not belong to restaurant ID " + restaurantId;
        }

        String itemName = menuItem.getName();
        menuItemRepository.delete(menuItem);
        return "Menu item '" + itemName + "' deleted successfully";
    }
}