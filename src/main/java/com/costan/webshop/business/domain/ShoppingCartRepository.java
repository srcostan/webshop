package com.costan.webshop.business.domain;

public interface ShoppingCartRepository {

    ShoppingCart getShoppingCart();

    void addShoppingCartEntry(ShoppingCartEntry shoppingCartEntry);
}
