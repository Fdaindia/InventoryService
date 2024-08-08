package com.example.crm.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.crm.Model.Inventory;
import com.example.crm.Service.InventoryService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    private final String uploadDir = "uploaded-images/";

    @PostMapping("/add")
    public ResponseEntity<Inventory> addProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("supplier") String supplier,
            @RequestParam("leadId") String leadId,
            @RequestParam("sku") String sku,
            @RequestParam("location") String location,
            @RequestParam("createdAt") String createdAt,
            @RequestParam("updatedAt") String updatedAt,
            @RequestParam("manufacturer") String manufacturer,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("costPrice") Double costPrice,
            @RequestParam("sellingPrice") Double sellingPrice,
            @RequestParam("status") String status,
            @RequestParam("notes") String notes,
            @RequestParam("returnPolicy") String returnPolicy,
            @RequestParam("stockThreshold") Integer stockThreshold
    ) throws IOException {
        LocalDate createdAtDate = LocalDate.parse(createdAt);
        LocalDate updatedAtDate = LocalDate.parse(updatedAt);
        LocalDate expiryDateDate = LocalDate.parse(expiryDate);

        Inventory inventory = inventoryService.addProduct(file, supplier, leadId, sku, location, createdAtDate, updatedAtDate, manufacturer, expiryDateDate, costPrice, sellingPrice, status, notes, returnPolicy, stockThreshold);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/images")
    public ResponseEntity<FileSystemResource> getImage(@RequestParam String filename) {
        File file = new File(uploadDir + File.separator + filename);
        if (file.exists()) {
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // Adjust content type as needed
                    .body(fileSystemResource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Inventory> updateProduct(
            @RequestParam("id") String id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "supplier", required = false) String supplier,
            @RequestParam(value = "leadId", required = false) String leadId,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "createdAt", required = false) String createdAt,
            @RequestParam(value = "updatedAt", required = false) String updatedAt,
            @RequestParam(value = "manufacturer", required = false) String manufacturer,
            @RequestParam(value = "expiryDate", required = false) String expiryDate,
            @RequestParam(value = "costPrice", required = false) Double costPrice,
            @RequestParam(value = "sellingPrice", required = false) Double sellingPrice,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "returnPolicy", required = false) String returnPolicy,
            @RequestParam(value = "stockThreshold", required = false) Integer stockThreshold
    ) throws IOException {
        LocalDate createdAtDate = createdAt != null ? LocalDate.parse(createdAt) : null;
        LocalDate updatedAtDate = updatedAt != null ? LocalDate.parse(updatedAt) : null;
        LocalDate expiryDateDate = expiryDate != null ? LocalDate.parse(expiryDate) : null;

        Inventory updatedInventory = inventoryService.updateProduct(id, file, supplier, leadId, sku, location, createdAtDate, updatedAtDate, manufacturer, expiryDateDate, costPrice, sellingPrice, status, notes, returnPolicy, stockThreshold);
        if (updatedInventory != null) {
            return ResponseEntity.ok(updatedInventory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam String id) {
        boolean isDeleted = inventoryService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.ok("Product with ID " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
        }
    }
}