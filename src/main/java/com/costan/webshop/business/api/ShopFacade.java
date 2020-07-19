package com.costan.webshop.business.api;

import java.util.List;

public interface ShopFacade {

    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getProductsByCategory(String categoryId);
}
