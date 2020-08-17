package com.costan.webshop.business.api.dto;

public class ProductDTO {
    private Integer id;
    private String title;
    private Double price;

    public ProductDTO(Integer id, String title, Double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }
}
