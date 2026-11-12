package com.omar.practice.week1.wednesday;

import com.omar.practice.model.OrderLine;
import com.omar.practice.model.PriceBreakdown;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceCalculator {
    static final BigDecimal TAX_RATE = new BigDecimal("0.17");
    static final BigDecimal MEMBER_DISCOUNT = new BigDecimal("0.10");
    /** Members get 10% off the subtotal. Tax applies after the discount. */
    public PriceBreakdown calculate(List<OrderLine> lines, boolean member) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal total;

        for (OrderLine line : lines) {
            BigDecimal lineTotal = line.unitPrice().multiply(BigDecimal.valueOf(line.quantity()));
            subtotal = subtotal.add(lineTotal);
        }

        if(member){
            subtotal = subtotal.multiply(MEMBER_DISCOUNT);
        }
        total = subtotal.subtract(TAX_RATE.multiply(BigDecimal.valueOf(100)));
        return new PriceBreakdown(subtotal, member ? MEMBER_DISCOUNT : BigDecimal.ZERO , TAX_RATE, total);
    }
    /** Three friends split one order. The parts must add up to the total
     exactly. */
    public List<BigDecimal> split(BigDecimal total, int people) {
        List<BigDecimal> shares = new ArrayList<>();
        for(int i = 0; i < people; i++){
            BigDecimal sharePerPerson = total.divide(BigDecimal.valueOf(people));
            shares.add(sharePerPerson);
        }
        return shares;
    }
}
