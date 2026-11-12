package com.omar.practice.model;

import java.math.BigDecimal;

public record PriceBreakdown(BigDecimal subtotal, BigDecimal discount, BigDecimal tax, BigDecimal total) {

}