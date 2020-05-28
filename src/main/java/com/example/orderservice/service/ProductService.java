package com.example.orderservice.service;

import com.example.orderservice.db.ProductDb;
import com.example.orderservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class ProductService {

   private final ProductDb productList;

   @Autowired
    public ProductService(ProductDb productList) {
        this.productList = productList;
    }

    public List<Product> getProductList (){
       return this.productList.getProductList();
    }

    public void addProductToList(Product product){
        this.productList.addProduct(product);
    }

    public List<Product> getProductByName(String name) {
        return productList.searchProductsByName(name);
    }
}
