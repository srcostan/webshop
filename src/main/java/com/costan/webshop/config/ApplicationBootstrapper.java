package com.costan.webshop.config;

import com.costan.webshop.business.api.ShopFacade;
import com.costan.webshop.business.api.impl.ShopFacadeImpl;
import com.costan.webshop.business.domain.repo.CategoryRepository;
import com.costan.webshop.business.domain.repo.ProductRepository;
import com.costan.webshop.business.domain.repo.ShoppingCartRepository;
import com.costan.webshop.business.domain.repo.impl.CategoryRepositoryImpl;
import com.costan.webshop.business.domain.repo.impl.ProductRepositoryImpl;
import com.costan.webshop.business.domain.repo.impl.ShoppingCartRepositoryImpl;
import com.costan.webshop.persistence.Database;
import com.costan.webshop.persistence.DatabaseImpl;
import com.costan.webshop.persistence.DbConfig;
import com.costan.webshop.web.framework.router.Router;

public class ApplicationBootstrapper {

    private static final String DB_DIRECTORY_PATH = "database_tables/";
    private static final String TABLE_SUFFIX = "_table";
    private static final String TABLE_NAME_SEPARATOR = "_";
    private static final String COLUMN_SEPARATOR = "###";


    public Router getRouter() {
        Router router = new Router();
        String[] controllersClassNames = new String[] {
                "com.costan.webshop.web.controller.FrontPageController",
                "com.costan.webshop.web.controller.ProductsController",
                "com.costan.webshop.web.controller.ShoppingCartController"
        };
        try {
            ShopFacade shopFacade = getFacade();
            for (String controllerName : controllersClassNames) {
                Class controllerClass = Class.forName(controllerName);
                router.registerController(controllerClass.getConstructor(ShopFacade.class)
                        .newInstance(shopFacade));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return router;
    }

    private ShopFacade getFacade() {
        Database database = getDatabase();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(database);
        ProductRepository productRepository = new ProductRepositoryImpl(database);
        ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepositoryImpl(database);
        return new ShopFacadeImpl(productRepository, categoryRepository, shoppingCartRepository);
    }

    private Database getDatabase() {
        DbConfig dbConfig =
                new DbConfig(DB_DIRECTORY_PATH, COLUMN_SEPARATOR, TABLE_NAME_SEPARATOR, TABLE_SUFFIX);
        DatabaseImpl database = new DatabaseImpl(dbConfig);
        try {
            registerEntities(database);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return database;
    }

    private void registerEntities(DatabaseImpl dbImpl) throws ClassNotFoundException {
        String[] entityClassNames = new String[] {
                "com.costan.webshop.business.domain.model.Product",
                "com.costan.webshop.business.domain.model.Category",
                "com.costan.webshop.business.domain.model.ShoppingCartEntry"
        };
        for (String className : entityClassNames) {
            Class entityClass = Class.forName(className);
            dbImpl.registerEntity(entityClass);
        }
    }
}
