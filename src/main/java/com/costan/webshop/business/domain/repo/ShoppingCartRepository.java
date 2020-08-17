package com.costan.webshop.business.domain.repo;

import com.costan.webshop.business.domain.model.ShoppingCart;
import com.costan.webshop.business.domain.model.ShoppingCartEntry;

public interface ShoppingCartRepository {

    ShoppingCart getShoppingCart();

    void addShoppingCartEntry(ShoppingCartEntry shoppingCartEntry);

    void emptyShoppingCart();
}
