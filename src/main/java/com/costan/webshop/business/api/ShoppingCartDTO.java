package com.costan.webshop.business.api;

import java.util.List;

public class ShoppingCartDTO {

    private List<ProductDTO> products;
    private Double totalAmount;

    public ShoppingCartDTO(List<ProductDTO> products, Double totalAmount) {
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
