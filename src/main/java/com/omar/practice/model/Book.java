package com.omar.practice.model;

import java.math.BigDecimal;

    public record Book(String isbn, String title, String author, String category,
                       BigDecimal price) {}
