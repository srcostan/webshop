package com.costan.webshop.business.domain;

import com.costan.webshop.persistence.annotation.DbColumn;
import com.costan.webshop.persistence.annotation.DbEntity;
import com.costan.webshop.persistence.annotation.Id;

@DbEntity
public class Product {
    @Id
    @DbColumn
    private Integer id;

    @DbColumn
    private String title;

    @DbColumn
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
