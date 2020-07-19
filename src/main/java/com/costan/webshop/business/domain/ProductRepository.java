package com.costan.webshop.business.domain;

import java.util.List;

public interface ProductRepository {

    List<Product> findAllProducts();
}
