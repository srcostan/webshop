package com.costan.webshop.business.domain;

public class DomainServiceLocator {

    private static CategoryRepository categoryRepository;
    private static ProductRepository productRepository;

    public CategoryRepository getCategoryRepository() {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepositoryImpl();
        }
        return categoryRepository;
    }

    public ProductRepository getProductRepository() {
        if (productRepository == null) {
            productRepository = new ProductRepositoryImpl();
        }
        return productRepository;
    }
}
