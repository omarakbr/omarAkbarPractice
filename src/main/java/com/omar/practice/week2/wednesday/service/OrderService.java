package com.omar.practice.week2.wednesday.service;

import com.omar.practice.model.OrderLine;
import com.omar.practice.model.OrderStatus;
import com.omar.practice.model.MutableOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderService {

    public void changeStatus(MutableOrder order, OrderStatus next, LocalDate today) {

        if (!order.status.canMoveTo(next, order, today)) {
            throw new IllegalStateException(
                    "Invalid move: Cannot transition to : " + next
            );
        }
        order.status = next;
        order.history.add(next.name());
    }

    public BigDecimal cancelLines(MutableOrder order, List<String> isbns, LocalDate today) {
//        took help from ai here
        BigDecimal refund = BigDecimal.ZERO;
        var iterator = order.lines.iterator();
        while (iterator.hasNext()) {
            OrderLine line = iterator.next();
            if (isbns.contains(line.isbn())) {
                if (order.status == OrderStatus.PAID) {
                    refund = refund.add(line.unitPrice());
                }
                iterator.remove();
            }
        }

        order.refundedAmount = order.refundedAmount.add(refund);

        if (order.lines.isEmpty()) {
            changeStatus(order, OrderStatus.CANCELLED, today);
        }
        return refund;
    }
}
