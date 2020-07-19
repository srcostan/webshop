package com.costan.webshop.business.api;

public class ApiServiceLocator {

    private static ShopFacade shopFacade;

    public ShopFacade getShopFacade() {
        if (shopFacade == null) {
            shopFacade = new ShopFacadeImpl();
        }
        return shopFacade;
    }
}
