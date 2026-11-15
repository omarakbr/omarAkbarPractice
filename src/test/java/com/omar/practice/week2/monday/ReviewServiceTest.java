package com.omar.practice.week2.monday;

import com.omar.practice.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    @ParameterizedTest(name = "member={0}, status={1}, isBanned={2}, inOrder={3} -> expected={4}")
    @CsvSource({
            // isMember, status, isBanned, inOrder, expected
            "true,  shipped,   true,  true,  false", // not allowed
            "true,  shipped,   true,  false, false", // not allowed
            "true,  shipped,   false, true,  true",  // allowed
            "true,  shipped,   false, false, false", // not allowed (need to see why failing)
            "true,  cancelled, true,  true,  false", // not allowed
            "true,  cancelled, true,  false, false", // not allowed
            "true,  cancelled, false, true,  false", // not allowed
            "true,  cancelled, false, false, false", // not allowed
            "false, shipped,   true,  true,  false", // not allowed
            "false, shipped,   true,  false, false", // not allowed
            "false, shipped,   false, true,  false", // not allowed
            "false, shipped,   false, false, false", // not allowed
            "false, cancelled, true,  true,  false", // not allowed
            "false, cancelled, true,  false, false", // not allowed
            "false, cancelled, false, true,  false", // not allowed
            "false, cancelled, false, false, false"  // not allowed
    })
    void testReviewPolicy(boolean isMember, String statusStr, boolean isBanned, boolean inOrder, boolean expected) {
        // Arrange
        OrderStatus status = statusStr.equals("shipped") ? OrderStatus.SHIPPED : OrderStatus.CANCELLED;
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId,"Omar Akbar","omar.akbar@savyat.com",isMember, isBanned);
        Book book = new Book("12345","Demo Book","Mark Williams","FICTION", BigDecimal.valueOf(500));
        OrderLine orderLine = new OrderLine("12345",1,BigDecimal.valueOf(500));
        Order order = new Order(UUID.randomUUID(),customerId, List.of(orderLine),status, LocalDateTime.now(),null);

        ReviewService service = new ReviewService();

        // Act
        boolean actual = service.canReview(customer, order, book);

        // Assert
        assertEquals(expected, actual);
    }

}