package com.codegym.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart {
    private Map<Product, Integer> products = new HashMap<>();

    public Cart() {
    }

    public Cart(Map<Product, Integer> products) {
        this.products = products;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
    private boolean checkItemInCart(Product product) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                if(entry.getKey().getId().equals(product.getId())){
                return true;
            }
        }
        return false;
    }

    public Map.Entry<Product, Integer> selectItemInCart(Product product) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if(entry.getKey().getId().equals(product.getId())){
                return entry;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        if (checkItemInCart(product)) {
            Map.Entry<Product, Integer> itemEntry = selectItemInCart(product);
            Integer newQuantity = itemEntry.getValue() + 1;
            products.replace(itemEntry.getKey(),newQuantity);
        } else {
            products.put(product, 1);
        }
    }

    public Integer countProductQuantity() {
        Integer productQuantity = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            productQuantity += entry.getValue();
        }
        return productQuantity;
    }

    public Integer countItemQuantity() {
        return products.size();
    }

    public float countToTalPayment() {
        float result = 0f;

        for (Map.Entry<Product, Integer>entry : products.entrySet()) {
            result += (float) entry.getKey().getPrice() * entry.getValue();
        }

        return result;
    }

    public void removeCartItem(Product product) {
        if (checkItemInCart(product)) {
            Map.Entry<Product, Integer> entry = selectItemInCart(product);
            if (entry.getValue() == 1) {
                products.remove(entry.getKey());
            } else {
                Integer newQuantity = entry.getValue() - 1;
                products.replace(entry.getKey(),newQuantity);
            }

        }
    }

    public void updateCartItem(Product product, Integer quantity) {
        if (checkItemInCart(product)) {
            Map.Entry<Product, Integer> entry = selectItemInCart(product);
            if (quantity == 0) {
                products.remove(entry.getKey());
            } else {
                products.replace(entry.getKey(), quantity);
            }
        }
    }

    public void deleteCartItem(Product product) {
        if (checkItemInCart(product)) {
            Map.Entry<Product, Integer> entry = selectItemInCart(product);
            products.remove(entry.getKey());
        }
    }
}
