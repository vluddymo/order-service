package com.example.orderservice.controller;

import com.example.orderservice.db.OrderDb;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OrderDb orderdb;




    @BeforeEach
    public void resetDatabase(){
        orderdb.clearDb();
        orderdb.addOrder(new Order("1",List.of(
                new Product("4", "Funghi")
        )));
    }

  @Test
  public void getOrderListShouldReturnOneOrder(){

      //GIVEN

      //WHEN
      ResponseEntity<Order[]> response = restTemplate.getForEntity("http://localhost:"+port+"/orders", Order[].class);
      Order[] orders = response.getBody();
      HttpStatus httpStatus = response.getStatusCode();

      //THEN
      assertEquals(1,orders.length);
      assertEquals(HttpStatus.OK, httpStatus);

  }

    @Test
    public void getOrderByIdShouldReturnOneOrder(){

        //GIVEN

        //WHEN
        ResponseEntity<Order> response = restTemplate.getForEntity("http://localhost:"+port+"/orders/1", Order.class);
        Order orders = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();

        //THEN
        assertEquals("1",orders.getId());
        assertEquals(HttpStatus.OK, httpStatus);
        //assertEquals(1,orders[0].getId());
        assertEquals(List.of(new Product("4", "Funghi")),orders.getProducts());

    }

    @Test
    public void putOrderShouldAddOrderToDatabase() {
        //GIVEN
        HttpEntity<Order> requestEntity = new HttpEntity<>(new Order("2", List.of(
                new Product("Margherita", "2"),
                new Product("Cipolla", "1"))));

        //WHEN
        ResponseEntity<Order> response = restTemplate.exchange("http://localhost:"+port+"/orders",HttpMethod.PUT,requestEntity, Order.class);
        Order orders = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();

        //THEN
        assertTrue(orderdb.getOrderList().contains(new Order("2", List.of(
                new Product("Margherita", "2"),
                new Product("Cipolla", "1")))));
    }

    @Test
    public void getOrderByIdShouldReturnNewOrder(){

        //GIVEN
        orderdb.addOrder(new Order("3", List.of(
                new Product("Prosciutto", "4"),
                new Product("Caprese", "5"))));

        //WHEN
        ResponseEntity<Order> response = restTemplate.getForEntity("http://localhost:"+port+"/orders/3", Order.class);
        Order orders = response.getBody();
        HttpStatus httpStatus = response.getStatusCode();

        //THEN
        assertEquals("3",orders.getId());
        assertEquals(HttpStatus.OK, httpStatus);
        assertTrue(orders.getProducts().contains(new Product("Prosciutto", "4")));
        assertTrue(orders.getProducts().contains(new Product("Caprese", "5")));

    }


}