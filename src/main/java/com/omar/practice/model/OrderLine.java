package com.omar.practice.model;

import java.math.BigDecimal;


    public record OrderLine(String isbn, int quantity, BigDecimal unitPrice) {}
