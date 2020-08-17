package com.costan.webshop.business.domain.repo.impl;

import com.costan.webshop.business.domain.model.Product;
import com.costan.webshop.business.domain.model.ShoppingCart;
import com.costan.webshop.business.domain.model.ShoppingCartEntry;
import com.costan.webshop.business.domain.repo.ShoppingCartRepository;
import com.costan.webshop.persistence.Database;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {

    private Database database;

    public ShoppingCartRepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public ShoppingCart getShoppingCart() {
        List<ShoppingCartEntry> entryList = database.getAllRecords(ShoppingCartEntry.class);
        List<Product> allProducts = database.getAllRecords(Product.class);
        List<Product> productsInCart = entryList.stream().map(e -> getProduct(allProducts, e))
                .collect(Collectors.toList());
        return new ShoppingCart(productsInCart);
    }

    @Override
    public void addShoppingCartEntry(ShoppingCartEntry shoppingCartEntry) {
        database.persist(shoppingCartEntry);
    }

    @Override
    public void emptyShoppingCart() {
        database.deleteAllRecords(ShoppingCartEntry.class);
    }

    private Product getProduct(List<Product> products, ShoppingCartEntry entry) {
        return products.stream().filter(p -> p.getId().equals(entry.getProductId())).findFirst().get();
    }
}
