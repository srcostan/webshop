package com.costan.webshop.business.domain;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAllCategories();
}
