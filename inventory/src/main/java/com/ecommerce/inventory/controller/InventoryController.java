package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private final InventoryService inventoryService;

    @PostMapping
    public Inventory addInventory(@RequestBody Inventory request) {
        Inventory inventory =  inventoryService.createOrUpdateInventory(request);
        return inventoryService.getInventoryById(inventory.getProductId());
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable("productId") Long productId) {
        return inventoryService.getInventoryById(productId);
    }

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryService.getAllInventory();
    }
}
