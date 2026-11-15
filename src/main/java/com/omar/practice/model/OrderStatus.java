package com.omar.practice.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum OrderStatus {
    CART {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            return next == PLACED;
        }
    },
    PLACED {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            return next == PAID || next == CANCELLED;
        }
    },
    PAID {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            return next == SHIPPED || next == CANCELLED || next == REFUNDED;
        }
    },
    SHIPPED {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            return next == DELIVERED;
        }
    },
    DELIVERED {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            if (next != REFUNDED || order.deliveredAt == null) return false;
            return ChronoUnit.DAYS.between(order.deliveredAt.toLocalDate(), today) <= 14;
        }
    },
    CANCELLED {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            return false;
        }
    },
    REFUNDED {
        @Override
        public boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today) {
            return false;
        }
    };

    public abstract boolean canMoveTo(OrderStatus next, MutableOrder order, LocalDate today);
}
