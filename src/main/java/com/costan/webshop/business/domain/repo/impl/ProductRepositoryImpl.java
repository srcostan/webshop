package com.costan.webshop.business.domain.repo.impl;

import com.costan.webshop.business.domain.model.Product;
import com.costan.webshop.business.domain.repo.ProductRepository;
import com.costan.webshop.persistence.Database;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private Database database;

    public ProductRepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Product> findAllProducts() {
        return database.getAllRecords(Product.class);
    }
}
