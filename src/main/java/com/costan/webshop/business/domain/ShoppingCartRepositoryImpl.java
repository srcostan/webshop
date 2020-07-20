package com.costan.webshop.business.domain;

import com.costan.webshop.persistence.Database;
import com.costan.webshop.persistence.DbServiceLocator;

import java.util.List;
import java.util.stream.Collectors;

class ShoppingCartRepositoryImpl implements ShoppingCartRepository {

    private Database database;

    public ShoppingCartRepositoryImpl() {
        this(new DbServiceLocator());
    }

    public ShoppingCartRepositoryImpl(DbServiceLocator dbServiceLocator) {
        database = dbServiceLocator.getDatabase();
    }

    @Override
    public ShoppingCart getShoppingCart() {
        List<ShoppingCartEntry> entryList = database.getAllRecords(ShoppingCartEntry.class);
        List<Product> allProducts = database.getAllRecords(Product.class);
        List<Product> productsInCart = allProducts.stream().filter(p -> isPresentInEntriesList(p, entryList)).collect(Collectors.toList());
        return new ShoppingCart(productsInCart);
    }

    @Override
    public void addShoppingCartEntry(ShoppingCartEntry shoppingCartEntry) {
        database.persist(shoppingCartEntry);
    }

    private boolean isPresentInEntriesList(Product product, List<ShoppingCartEntry> entries) {
        return entries.stream().anyMatch(e -> e.getProductId().equals(product.getId()));
    }
}
