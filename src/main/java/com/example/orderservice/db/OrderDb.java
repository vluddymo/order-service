package com.example.orderservice.db;

import com.example.orderservice.models.Order;
import com.example.orderservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDb {

    private final List<Order> orderList = new ArrayList<>(List.of(
            new Order("1",List.of(
                    new Product("4", "Funghi")
            ))
    ));


    public void addOrder (Order order){
        this.orderList.add(order);
    }

    public List<Order> getOrderList(){
        return this.orderList;
    }

    public void clearDb() {
        orderList.clear();
    }
}
