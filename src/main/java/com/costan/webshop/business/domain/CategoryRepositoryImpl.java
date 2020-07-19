package com.costan.webshop.business.domain;

import com.costan.webshop.persistence.Database;
import com.costan.webshop.persistence.DbServiceLocator;

import java.util.List;

class CategoryRepositoryImpl implements CategoryRepository {

    private Database database;

    CategoryRepositoryImpl() {
        this(new DbServiceLocator());
    }

    CategoryRepositoryImpl(DbServiceLocator dbServiceLocator) {
        database = dbServiceLocator.getDatabase();
    }
    @Override
    public List<Category> findAllCategories() {
        return database.getAllRecords(Category.class);
    }
}
