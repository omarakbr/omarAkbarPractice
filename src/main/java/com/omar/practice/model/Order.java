package com.omar.practice.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Order(
        UUID id,
        UUID customerId,
        List<OrderLine> lines,
        OrderStatus status,
        LocalDateTime placedAt,
        String couponCode) {
}
