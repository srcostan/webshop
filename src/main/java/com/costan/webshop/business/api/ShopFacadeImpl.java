package com.costan.webshop.business.api;

import com.costan.webshop.business.domain.CategoryRepository;
import com.costan.webshop.business.domain.DomainServiceLocator;
import com.costan.webshop.business.domain.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ShopFacadeImpl implements ShopFacade {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ShopFacadeImpl() {
        this(new DomainServiceLocator());
    }

    public ShopFacadeImpl(DomainServiceLocator domainServiceLocator) {
        productRepository = domainServiceLocator.getProductRepository();
        categoryRepository = domainServiceLocator.getCategoryRepository();
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllCategories()
                .stream().map(c -> new CategoryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String categoryId) {
        return productRepository.findAllProducts().stream()
                .map(p -> new ProductDTO(p.getId(), "cat selected " + categoryId + p.getTitle(), p.getPrice()))
                .collect(Collectors.toList());
    }
}
