package com.costan.webshop.web.controller;

import com.costan.webshop.business.api.ApiServiceLocator;
import com.costan.webshop.business.api.ShopFacade;
import com.costan.webshop.web.framework.ModelAndView;
import com.costan.webshop.web.framework.annotation.Path;
import com.costan.webshop.web.framework.annotation.PostMapping;
import com.costan.webshop.web.framework.annotation.WebController;

@WebController
@Path("shoppingCart")
public class ShoppingCartController {

    private ShopFacade shopFacade;

    public ShoppingCartController() {
        this(new ApiServiceLocator());
    }

    public ShoppingCartController(ApiServiceLocator apiServiceLocator) {
        shopFacade = apiServiceLocator.getShopFacade();
    }

    @PostMapping
    @Path("/addProductToCart")
    public ModelAndView getProductsByCategory(String productId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addData("products", shopFacade.getProductsByCategory(productId));
        modelAndView.setView("/templates/components/products.jsp");
        return modelAndView;
    }
}
