package com.costan.webshop.web.controller;

import com.costan.webshop.business.api.ApiServiceLocator;
import com.costan.webshop.business.api.ShopFacade;
import com.costan.webshop.web.framework.ModelAndView;
import com.costan.webshop.web.framework.annotation.GetMapping;
import com.costan.webshop.web.framework.annotation.Path;
import com.costan.webshop.web.framework.annotation.WebController;

@WebController
@Path("products")
public class ProductsController {

    private ShopFacade shopFacade;

    public ProductsController() {
        this(new ApiServiceLocator());
    }

    public ProductsController(ApiServiceLocator apiServiceLocator) {
        shopFacade = apiServiceLocator.getShopFacade();
    }

    @GetMapping
    @Path("/getByCategory")
    public ModelAndView getProductsByCategory(String categoryId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addData("products", shopFacade.getProductsByCategory(categoryId));
        modelAndView.setView("/templates/components/products.jsp");
        return modelAndView;
    }

}
