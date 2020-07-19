package com.costan.webshop.web.controller;

import com.costan.webshop.business.api.ApiServiceLocator;
import com.costan.webshop.business.api.ShopFacade;
import com.costan.webshop.web.framework.ModelAndView;
import com.costan.webshop.web.framework.annotation.GetMapping;
import com.costan.webshop.web.framework.annotation.Path;
import com.costan.webshop.web.framework.annotation.WebController;

@WebController
@Path("frontPage")
public class FrontPageController {

    private ShopFacade shopFacade;

    public FrontPageController() {
        this(new ApiServiceLocator());
    }

    public FrontPageController(ApiServiceLocator apiServiceLocator) {
        shopFacade = apiServiceLocator.getShopFacade();
    }

    @GetMapping
    @Path("/")
    public ModelAndView retrieveFrontPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addData("categories", shopFacade.getAllCategories());
        modelAndView.setView("/templates/frontPage.jsp");
        return modelAndView;
    }

}
