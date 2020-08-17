package com.costan.webshop.business.api;

import com.costan.webshop.business.api.dto.CategoryDTO;
import com.costan.webshop.business.api.dto.ProductDTO;
import com.costan.webshop.business.api.dto.ShoppingCartDTO;

import java.util.List;

public interface ShopFacade {

    List<CategoryDTO> getAllCategories();

    List<ProductDTO> getProductsByCategory(String categoryId);

    ShoppingCartDTO retrieveShoppingCart();

    void emptyShoppingCart();

    void addProductToShoppingCart(Integer productId);
}
