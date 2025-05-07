package com.pluralsight;

public class Product {
    private String sku;
    private String productName;
    private double productPrice;

    public Product(String sku, String productName, double productPrice) {
        this.sku = sku;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getSku() {
        return sku;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }
}
