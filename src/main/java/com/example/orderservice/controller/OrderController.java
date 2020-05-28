package com.example.orderservice.controller;

import com.example.orderservice.models.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrderList (){
        return this.orderService.getOrderList();
    }

    @PutMapping
    public Order addOrder(@RequestBody Order order){
        orderService.addOrder(order);
        return order;
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable String id){
        return orderService.getOrderById(id);
    }

}
