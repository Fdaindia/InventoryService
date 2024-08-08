package com.example.crm.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.crm.Model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
}

