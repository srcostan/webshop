package com.costan.webshop.business.domain.repo;

import com.costan.webshop.business.domain.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAllProducts();
}
