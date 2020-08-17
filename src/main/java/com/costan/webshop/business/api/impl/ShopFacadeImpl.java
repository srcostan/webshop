package com.costan.webshop.business.api.impl;

import com.costan.webshop.business.api.ShopFacade;
import com.costan.webshop.business.api.dto.CategoryDTO;
import com.costan.webshop.business.api.dto.ProductDTO;
import com.costan.webshop.business.api.dto.ShoppingCartDTO;
import com.costan.webshop.business.domain.model.Category;
import com.costan.webshop.business.domain.model.ShoppingCart;
import com.costan.webshop.business.domain.model.ShoppingCartEntry;
import com.costan.webshop.business.domain.repo.CategoryRepository;
import com.costan.webshop.business.domain.repo.ProductRepository;
import com.costan.webshop.business.domain.repo.ShoppingCartRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShopFacadeImpl implements ShopFacade {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShopFacadeImpl(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllCategories()
                .stream().map(c -> new CategoryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String categoryId) {
        Optional<Category> category =  categoryRepository.findCategoryById(Integer.valueOf(categoryId));
        if (category.isPresent()) {
            return category.get().getProducts().stream()
                    .map(p -> new ProductDTO(p.getId(), p.getTitle(), p.getPrice()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public ShoppingCartDTO retrieveShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCart();
        List<ProductDTO> productsDTO = shoppingCart.getProducts().stream()
                .map(p -> new ProductDTO(p.getId(), p.getTitle(), p.getPrice()))
                .collect(Collectors.toList());
        return new ShoppingCartDTO(productsDTO, shoppingCart.getTotalPrice());
    }

    @Override
    public void emptyShoppingCart() {
        shoppingCartRepository.emptyShoppingCart();
    }

    @Override
    public void addProductToShoppingCart(Integer productId) {
        ShoppingCartEntry shoppingCartEntry = new ShoppingCartEntry(productId);
        shoppingCartRepository.addShoppingCartEntry(shoppingCartEntry);
    }
}
