package com.example.orderservice.db;

import com.example.orderservice.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDb {

    private List<Product> productList = new ArrayList<>(List.of(
            new Product("Margherita", "1"),
            new Product("Cipolla", "2"),
            new Product("Tonno", "3"),
            new Product("Prosciutto", "4"),
            new Product("Caprese", "5"),
            new Product("Funghi", "6"),
            new Product("Napoli", "7"),
            new Product("Provence", "8"),
            new Product("Reggiano", "9")

    ));

    public void addProduct(Product product) {
        this.productList.add(product);
    }

    public List<Product> getProductList() {
        return this.productList;

    }


    public boolean productMatchesQuery(Product product, String name) {

        return product.getName().toLowerCase().startsWith(name.toLowerCase());
    }

    public List<Product> searchProductsByName(String name) {

        List<Product> matchingProducts = new ArrayList<>();

        for (Product product : productList) {
            if (productMatchesQuery(product, name)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    public void clearDb() {
        productList.clear();
    }
}
