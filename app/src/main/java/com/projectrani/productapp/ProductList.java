package com.projectrani.productapp;

public class ProductList {

    private String title, desc, imageURL, productId, gtPrice, mtPrice, gtBatam, mtBatam, barcode, unit, rule;


    public ProductList(String title, String desc, String imageURL, String productId, String gtPrice, String mtPrice, String gtBatam, String mtBatam, String barcode, String unit, String rule) {
        this.title = title;
        this.desc = desc;
        this.imageURL = imageURL;
        this.productId = productId;
        this.gtPrice = gtPrice;
        this.mtPrice = mtPrice;
        this.gtBatam = gtBatam;
        this.mtBatam = mtBatam;
        this.barcode = barcode;
        this.unit = unit;
        this.rule = rule;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getProductId() {
        return productId;
    }

    public String getGtPrice() {
        return gtPrice;
    }

    public String getMtPrice() {
        return mtPrice;
    }

    public String getGtBatam() {
        return gtBatam;
    }

    public String getMtBatam() {
        return mtBatam;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUnit() {
        return unit;
    }

    public String getRule() {
        return rule;
    }
}
