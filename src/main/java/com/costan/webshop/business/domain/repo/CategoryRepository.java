package com.costan.webshop.business.domain.repo;

import com.costan.webshop.business.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> findAllCategories();

    Optional<Category> findCategoryById(Integer id);
}
