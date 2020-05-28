package com.example.orderservice.service;

import com.example.orderservice.db.OrderDb;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.Product;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Service
public class OrderService {

    private final OrderDb orderList;

    @Autowired
    public OrderService(OrderDb orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return this.orderList.getOrderList();
    }

    public void addOrder(Order order) {
        orderList.addOrder(order);
    }

    public Order getOrderById(String id) {

        for (Order order : orderList.getOrderList()) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order does not exist");
    }
}
