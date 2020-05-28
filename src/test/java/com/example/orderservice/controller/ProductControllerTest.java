package com.example.orderservice.controller;

import com.example.orderservice.db.OrderDb;
import com.example.orderservice.db.ProductDb;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductDb productDb;

    @BeforeEach
    public void resetDatabase(){
        productDb.clearDb();
        productDb.addProduct(new Product("Diabolo", "18"));

    }
    @Test
    public void addProductToListShouldPutNewProductToDatabase(){

        //GIVEN
        HttpEntity<Product> requestEntity = new HttpEntity<>(new Product("Cipolla", "1"));

        //WHEN
        ResponseEntity<Product> response = restTemplate.exchange("http://localhost:"+port+"/products", HttpMethod.PUT,requestEntity, Product.class);
        Product product = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();

        //THEN
        assertTrue(productDb.getProductList().contains(product));
        assertEquals(HttpStatus.OK,httpStatus);
    }

    @Test
    public void getProductListShouldContainProductAddedToDatabase(){

        //GIVEN
        productDb.addProduct(new Product("Spaghetti","20"));

        //WHEN
        ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:"+port+"/products", Product[].class);
        Product[] product = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();

        //THEN
        assertEquals(new Product("Spaghetti","20"),product[1]);
        assertEquals(HttpStatus.OK,httpStatus);
    }

    @Test
    public void findProductShouldFindProductAddedToDatabase(){

        //GIVEN
        productDb.addProduct(new Product("Spaghetti","20"));

        //WHEN
        ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:"+port+"/products/search?name=spa", Product[].class);
        Product[] product = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();

        //THEN
        assertEquals(new Product("Spaghetti","20"),product[0]);
        assertEquals(HttpStatus.OK,httpStatus);
    }
}