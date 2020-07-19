package com.costan.webshop.business.api;

import java.util.ArrayList;
import java.util.List;

public class ShopFacadeImpl implements ShopFacade {

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(new CategoryDTO(i, "category" + i));
        }
        return categories;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String categoryId) {
        List<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new ProductDTO(i, categoryId + " product" +  + i, 2.1 * i));
        }
        return products;
    }
}
