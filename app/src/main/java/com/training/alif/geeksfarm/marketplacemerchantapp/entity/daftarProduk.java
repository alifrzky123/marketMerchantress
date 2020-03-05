package com.training.alif.geeksfarm.marketplacemerchantapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class daftarProduk {
    @SerializedName("data")
    private ArrayList<Product> products;

    public daftarProduk (ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
