package com.costan.webshop.business.domain.model;

import com.costan.webshop.persistence.annotation.DbColumn;
import com.costan.webshop.persistence.annotation.DbEntity;

@DbEntity
public class ShoppingCartEntry {

    @DbColumn
    private Integer productId;

    public ShoppingCartEntry() {
    }

    public ShoppingCartEntry(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
