package com.example.crm.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.crm.Model.Inventory;
import com.example.crm.Repo.InventoryRepository;
import com.example.crm.Service.InventoryService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    private final String uploadDir = "uploaded-images/";

    @Override
    public Inventory addProduct(MultipartFile file, String supplier, String leadId, String sku, String location, LocalDate createdAt, LocalDate updatedAt, String manufacturer, LocalDate expiryDate, Double costPrice, Double sellingPrice, String status, String notes, String returnPolicy, Integer stockThreshold) throws IOException {
        String imageUrl = null;
        if (!file.isEmpty()) {
            try {
                // Create the directory if it doesn't exist
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Generate a unique filename and save the file locally
                String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
                File localFile = new File(uploadDir + File.separator + filename);
                try (FileOutputStream fos = new FileOutputStream(localFile)) {
                    fos.write(file.getBytes());
                }
                imageUrl = "http://localhost:8080/api/inventory/images/" + filename; // URL format
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Error saving file to local storage", e);
            }
        }

        Inventory inventory = new Inventory();
        inventory.setSupplier(supplier);
        inventory.setLeadId(leadId);
        inventory.setSku(sku);
        inventory.setLocation(location);
        inventory.setCreatedAt(createdAt);
        inventory.setUpdatedAt(updatedAt);
        inventory.setManufacturer(manufacturer);
        inventory.setExpiryDate(expiryDate);
        inventory.setCostPrice(costPrice);
        inventory.setSellingPrice(sellingPrice);
        inventory.setStatus(status);
        inventory.setNotes(notes);
        inventory.setImageUrl(imageUrl); // Set the URL for accessing the image
        inventory.setReturnPolicy(returnPolicy);
        inventory.setStockThreshold(stockThreshold);

        return inventoryRepository.save(inventory);
    }
    @Override
    public Inventory updateProduct(String id, MultipartFile file, String supplier, String leadId, String sku, String location, LocalDate createdAt, LocalDate updatedAt, String manufacturer, LocalDate expiryDate, Double costPrice, Double sellingPrice, String status, String notes, String returnPolicy, Integer stockThreshold) throws IOException {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (!optionalInventory.isPresent()) {
            return null;
        }
        
        Inventory inventory = optionalInventory.get();

        if (file != null && !file.isEmpty()) {
            try {
                // Create the directory if it doesn't exist
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Generate a unique filename and save the file locally
                String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
                File localFile = new File(uploadDir + File.separator + filename);
                try (FileOutputStream fos = new FileOutputStream(localFile)) {
                    fos.write(file.getBytes());
                }
                inventory.setImageUrl("http://localhost:8080/api/inventory/images/" + filename); // URL format
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Error saving file to local storage", e);
            }
        }

        if (supplier != null) inventory.setSupplier(supplier);
        if (leadId != null) inventory.setLeadId(leadId);
        if (sku != null) inventory.setSku(sku);
        if (location != null) inventory.setLocation(location);
        if (createdAt != null) inventory.setCreatedAt(createdAt);
        if (updatedAt != null) inventory.setUpdatedAt(updatedAt);
        if (manufacturer != null) inventory.setManufacturer(manufacturer);
        if (expiryDate != null) inventory.setExpiryDate(expiryDate);
        if (costPrice != null) inventory.setCostPrice(costPrice);
        if (sellingPrice != null) inventory.setSellingPrice(sellingPrice);
        if (status != null) inventory.setStatus(status);
        if (notes != null) inventory.setNotes(notes);
        if (returnPolicy != null) inventory.setReturnPolicy(returnPolicy);
        if (stockThreshold != null) inventory.setStockThreshold(stockThreshold);

        return inventoryRepository.save(inventory);
    }

    @Override
    public boolean deleteProduct(String id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}