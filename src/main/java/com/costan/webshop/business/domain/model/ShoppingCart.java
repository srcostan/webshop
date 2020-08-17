package com.costan.webshop.business.domain.model;

import java.util.List;

public class ShoppingCart {

    private List<Product> products;

    public ShoppingCart(List<Product> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return products.stream().mapToDouble(p -> p.getPrice()).sum();
    }

    public List<Product> getProducts() {
        return products;
    }
}
