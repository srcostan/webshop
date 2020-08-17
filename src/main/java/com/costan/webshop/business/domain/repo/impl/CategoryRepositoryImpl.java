package com.costan.webshop.business.domain.repo.impl;

import com.costan.webshop.business.domain.model.Category;
import com.costan.webshop.business.domain.repo.CategoryRepository;
import com.costan.webshop.persistence.Database;

import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private Database database;

    public CategoryRepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Category> findAllCategories() {
        return database.getAllRecords(Category.class);
    }

    @Override
    public Optional<Category> findCategoryById(Integer id) {
        return database.getEntityByPrimaryKey(Category.class, id);
    }
}
