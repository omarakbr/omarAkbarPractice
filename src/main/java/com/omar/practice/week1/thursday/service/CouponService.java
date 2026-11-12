package com.omar.practice.week1.thursday.service;

import com.omar.practice.week1.thursday.Coupon;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CouponService {
    public BigDecimal discountFor(Coupon c, BigDecimal subtotal, LocalDate today) {
        if (c.type.equals("PERCENT")) {
            return subtotal.multiply(c.value).divide(new BigDecimal(100));
        } else if (c.type == "FIXED") {
            return c.value;
        }
        return BigDecimal.ZERO;
    }
}

// bug 3 -> this method never checks if the coupon is expired or not
// bug 4 -> not handling the coupon type "FREESHIP"
// bug 5 -> missing validation for the discounted value/percentage.
// bug 6 -> only the type is checked, the coupon code is never checked.
// bug 7 -> allowing same coupon to be applied more than once.


// for redesign i will go with the enum approach
