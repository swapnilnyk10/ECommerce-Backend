package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.repository.InventoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public Inventory addInventory(@RequestBody Inventory request) {

        Inventory inventory = inventoryRepository.findById(request.getProductId())
                .orElse(null);

        if (inventory == null) {
            // New product inventory
            inventory = new Inventory();
            inventory.setProductId(request.getProductId());
            inventory.setAvailableQuantity(request.getAvailableQuantity());
            inventory.setReservedQuantity(0);
        } else {
            // Existing product → increment stock
            inventory.setAvailableQuantity(
                    inventory.getAvailableQuantity() + request.getAvailableQuantity());
        }

        return inventoryRepository.save(inventory);
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable("productId") Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }
}
