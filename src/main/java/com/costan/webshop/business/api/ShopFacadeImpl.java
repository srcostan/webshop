package com.costan.webshop.business.api;

import com.costan.webshop.business.domain.CategoryRepository;
import com.costan.webshop.business.domain.DomainServiceLocator;
import com.costan.webshop.business.domain.ProductRepository;

import java.util.ArrayList;
import java.util.List;

class ShopFacadeImpl implements ShopFacade {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ShopFacadeImpl() {
        this(new DomainServiceLocator());
    }

    public ShopFacadeImpl(DomainServiceLocator domainServiceLocator) {
        productRepository = domainServiceLocator.getProductRepository();
        categoryRepository = domainServiceLocator.getCategoryRepository();
    }

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
