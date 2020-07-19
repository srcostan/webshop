package com.costan.webshop.business.domain;

import com.costan.webshop.persistence.Database;
import com.costan.webshop.persistence.DbServiceLocator;

import java.util.List;

class ProductRepositoryImpl implements ProductRepository {

    private Database database;

    ProductRepositoryImpl() {
        this(new DbServiceLocator());
    }

    ProductRepositoryImpl(DbServiceLocator dbServiceLocator) {
        database = dbServiceLocator.getDatabase();
    }

    @Override
    public List<Product> findAllProducts() {
        return database.getAllRecords(Product.class);
    }
}
