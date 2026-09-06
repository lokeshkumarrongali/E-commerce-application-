package com.example.SpringEcom.model.dto;

//import com.example.SpringEcom.model.OrderItemRequest;

import java.util.List;

public record OrderRequest(
        String customerName,
        String email,
        List<OrderItemRequest> items
) {
}
