package com.costan.webshop.business.domain;

import com.costan.webshop.persistence.annotation.DbColumn;
import com.costan.webshop.persistence.annotation.DbEntity;

@DbEntity
public class Category {

    @DbColumn
    private Integer id;

    @DbColumn
    private String name;

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
}
