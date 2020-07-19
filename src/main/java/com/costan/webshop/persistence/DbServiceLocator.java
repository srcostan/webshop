package com.costan.webshop.persistence;

import com.costan.webshop.business.domain.Category;
import com.costan.webshop.business.domain.Product;

public class DbServiceLocator {

    private static Database database;

    private static final String[] entityClassNames = new String[] {
      "com.costan.webshop.business.domain.Product",
      "com.costan.webshop.business.domain.Category"
    };

    public Database getDatabase() {
        if (database == null) {
            try {
                DatabaseImpl dbImpl = new DatabaseImpl();
                registerEntities(dbImpl);
                database = dbImpl;
                insertMockCategories();
                insertMockProducts();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return database;
    }

    private void registerEntities(DatabaseImpl dbImpl) throws ClassNotFoundException {
        for (String className : entityClassNames) {
            Class entityClass = Class.forName(className);
            dbImpl.registerEntity(entityClass);
        }
    }


    private void insertMockCategories() {
        for (int i = 1; i < 10; i++) {
            Category cat = new Category();
            cat.setId(i);
            cat.setName("category - " + i);
            database.persist(cat);
        }
    }

    private void insertMockProducts() {
        for (int i = 1; i < 10; i++) {
            Product prod = new Product();
            prod.setId(i);
            prod.setTitle("product - " + i);
            prod.setPrice(Math.random());
            database.persist(prod);
        }
    }
}
