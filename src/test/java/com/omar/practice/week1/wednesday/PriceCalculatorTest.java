package com.omar.practice.week1.wednesday;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {

    @Test
    void usingDoubleToAdd() {
        double a = 0.1;
        double b = 0.2;
        double sum = a + b;

        System.out.println("Double sum: " + sum);

        assertEquals(0.30000000000000004, sum);
        assertNotEquals(0.3, sum);
    }

    @Test
    void usingBigDecimalToAdd() {
        BigDecimal a = BigDecimal.valueOf(0.1);
        BigDecimal b = BigDecimal.valueOf(0.2);
        BigDecimal sum = a.add(b);

        System.out.println("Double sum: " + sum);

        assertEquals(BigDecimal.valueOf(0.3), sum);
        assertNotEquals(BigDecimal.valueOf(0.30000000000000004), sum);
    }

    @Test
    void printingTest() {
        System.out.println(new BigDecimal(0.1));
        System.out.println(BigDecimal.valueOf(0.1));
        System.out.println(new BigDecimal("0.1"));
    }

    @Test
    void scaleDifferenceTest() {
        BigDecimal price1 = new BigDecimal("10.0");
        BigDecimal price2 = new BigDecimal("10.00");

        System.out.printf("Price 1 = Price 2 : %b", price1.equals(price2));
        assertFalse(price1.equals(price2));
        assertNotEquals(price1,price2);

    }



//    @Test
//    void usingLongToAdd() {
//        int price1 = 5;
//        int price2 = 6;
//        long cent1 = 10;
//        long cent2 = 20;
//        double price = price1 + price2;
//
//        System.out.println("Double sum: " + sum);
//
//        assertEquals(0.30000000000000004, sum);
//        assertNotEquals(0.3, sum);
//    }
}