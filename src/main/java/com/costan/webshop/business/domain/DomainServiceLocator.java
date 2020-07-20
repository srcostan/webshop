package com.costan.webshop.business.domain;

public class DomainServiceLocator {

    private static CategoryRepository categoryRepository;
    private static ProductRepository productRepository;
    private static ShoppingCartRepository shoppingCartRepository;

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

    public ShoppingCartRepository getShoppingCartRepository() {
        if (shoppingCartRepository == null) {
            shoppingCartRepository = new ShoppingCartRepositoryImpl();
        }
        return shoppingCartRepository;
    }
}
