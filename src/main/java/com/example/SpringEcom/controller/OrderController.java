package com.example.SpringEcom.controller;


import com.example.SpringEcom.Repo.ProductRepo;
import com.example.SpringEcom.Service.OrderService;
import com.example.SpringEcom.model.dto.OrderRequest;
import com.example.SpringEcom.model.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {
@Autowired
private OrderService orderService;

    @PostMapping("/orders/place" )
    public ResponseEntity<OrderResponse> placeOrders(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);

    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
       List<OrderResponse> responses = orderService.getAllOrderResponses();
       return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
