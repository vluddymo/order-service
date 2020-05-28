package com.example.orderservice.controller;


import com.example.orderservice.models.Product;
import com.example.orderservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> listProductList(){
       return productService.getProductList();
    }

    @PutMapping
    public Product addProductToList(@RequestBody Product product){
        productService.addProductToList(product);
        return product;
    }

    @GetMapping("search")
    public List<Product> findProductByName(@RequestParam(required = false)String name){
        return productService.getProductByName(name);
    }
}
