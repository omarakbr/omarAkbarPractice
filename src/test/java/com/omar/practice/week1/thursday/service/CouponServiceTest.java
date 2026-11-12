package com.omar.practice.week1.thursday.service;

import com.omar.practice.week1.thursday.Coupon;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponServiceTest {

    @Test
    void shouldCatchExpiredCouponBeingApplied() {
        Coupon c = new Coupon();
        c.type = "PERCENT";
        c.value = new BigDecimal("50");
        c.expires = LocalDate.now().minusDays(1); // coupon expired

        CouponService service = new CouponService();
        BigDecimal discount = service.discountFor(c, new BigDecimal("100.00"), LocalDate.now());

        assertNotEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void shouldHandleOrRejectFreeShipType() {
        Coupon c = new Coupon();
        c.type = "FREESHIP";
        c.value = BigDecimal.valueOf(200);
        c.expires = LocalDate.now().plusDays(2);

        CouponService service = new CouponService();
        BigDecimal discount = service.discountFor(c, new BigDecimal("100.00"), LocalDate.now());

        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void shouldRejectInvalidCouponInvariants() {
        Coupon c = new Coupon();
        c.type = "PERCENT";
        c.value = new BigDecimal("150.00"); // Invalid: > 100%
        c.expires = LocalDate.now().plusDays(5);

        CouponService service = new CouponService();
        BigDecimal discount = service.discountFor(c, new BigDecimal("100.00"), LocalDate.now());

        assertEquals(BigDecimal.valueOf(150.00),discount);
    }

    @Test
    void shouldPreventApplyingSameCouponTwice() {
        Coupon c = new Coupon();
        c.code = "aasdfsdfwe";
        c.type = "PERCENT";
        c.value = new BigDecimal("10.00");
        c.expires = LocalDate.now().plusDays(1);
        BigDecimal subtotal = new BigDecimal("100.00");
        LocalDate today = LocalDate.now();

        CouponService service = new CouponService();
        BigDecimal appliedFirst = service.discountFor(c, subtotal, today);
        BigDecimal appliedSecond = service.discountFor(c, subtotal, today);

        assertEquals(BigDecimal.valueOf(10.00),appliedFirst);
        assertEquals(BigDecimal.ZERO,appliedSecond);

    }

}