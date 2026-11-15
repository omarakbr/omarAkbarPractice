package com.omar.practice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MutableOrder {
    public OrderStatus status;
    public List<OrderLine> lines;
    public BigDecimal shipping;
    public LocalDateTime deliveredAt;
    public List<String> history = new ArrayList<>(List.of("CART"));
    public BigDecimal refundedAmount = BigDecimal.ZERO;
}