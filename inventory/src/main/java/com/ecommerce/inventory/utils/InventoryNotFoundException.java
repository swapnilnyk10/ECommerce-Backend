package com.ecommerce.inventory.utils;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String message) {

        super(message);
    }
}
