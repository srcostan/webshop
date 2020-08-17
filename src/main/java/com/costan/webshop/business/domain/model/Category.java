package com.costan.webshop.business.domain.model;

import com.costan.webshop.persistence.annotation.DbColumn;
import com.costan.webshop.persistence.annotation.DbEntity;
import com.costan.webshop.persistence.annotation.DbManyToMany;
import com.costan.webshop.persistence.annotation.Id;

import java.util.List;

@DbEntity
public class Category {

    @Id
    @DbColumn
    private Integer id;

    @DbColumn
    private String name;

    @DbManyToMany
    private List<Product> products;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
