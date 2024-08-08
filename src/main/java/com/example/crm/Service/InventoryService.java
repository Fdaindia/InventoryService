package com.example.crm.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.crm.Model.Inventory;

import java.io.IOException;
import java.time.LocalDate;

public interface InventoryService {

    Inventory addProduct(MultipartFile file, String supplier, String leadId, String sku, String location, LocalDate createdAt, LocalDate updatedAt, String manufacturer, LocalDate expiryDate, Double costPrice, Double sellingPrice, String status, String notes, String returnPolicy, Integer stockThreshold) throws IOException;
    Inventory updateProduct(String id, MultipartFile file, String supplier, String leadId, String sku, String location, LocalDate createdAt, LocalDate updatedAt, String manufacturer, LocalDate expiryDate, Double costPrice, Double sellingPrice, String status, String notes, String returnPolicy, Integer stockThreshold) throws IOException;
    boolean deleteProduct(String id);
    // Other methods like getInventoryById, updateInventory, deleteInventory
}
