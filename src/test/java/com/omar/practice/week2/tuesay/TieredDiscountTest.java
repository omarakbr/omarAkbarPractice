package com.omar.practice.week2.tuesay;

import com.omar.practice.model.DiscountOrder;
import com.omar.practice.model.OrderLine;
import com.omar.practice.model.Tier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TieredDiscountTest {

    private TieredDiscount tieredDiscount;
    private List<Tier> standardTiers;

    @BeforeEach
    void setUp() {
        tieredDiscount = new TieredDiscount();
        standardTiers = List.of(
                new Tier(1, 2, 0),
                new Tier(3, 4, 10),
                new Tier(5, null, 15)
        );
    }

    @Test
    void testEmptyCart() {
        List<OrderLine> cart = List.of();
        List<BigDecimal> result = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.CHEAPEST_FIRST);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleItemCart() {
        List<OrderLine> cart = List.of(new OrderLine("978-1",1, BigDecimal.valueOf(1000)));
        // Position 1 gets 0% discount
        List<BigDecimal> result = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.CHEAPEST_FIRST);

        assertEquals(1, result.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.getFirst()));
    }

    @Test
    void testExactlyThreeItems() {
        // Prices: 300, 400, 500. Sorted CHEAPEST_FIRST: 300 (pos 1: 0%), 400 (pos 2: 0%), 500 (pos 3: 10% -> 50.00)
        List<OrderLine> cart = List.of(
                new OrderLine("A",1, BigDecimal.valueOf(500)),
                new OrderLine("B",1, BigDecimal.valueOf(300)),
                new OrderLine("C",1, BigDecimal.valueOf(400))
        );
        List<BigDecimal> result = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.CHEAPEST_FIRST);

        assertEquals(3, result.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(0))); // 300 -> 0
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(1))); // 400 -> 0
        assertEquals(0, BigDecimal.valueOf(50.00).compareTo(result.get(2))); // 500 -> 50
    }

    @Test
    void testQuantityGreaterThanOneExpandsProperly() {
        // Cart has 1 line with quantity 2 priced at 400. Expands to [400, 400].
        List<OrderLine> cart = List.of(new OrderLine("A",2, BigDecimal.valueOf(400)));
        List<BigDecimal> result = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.CHEAPEST_FIRST);

        assertEquals(2, result.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(0))); // pos 1: 0%
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(1))); // pos 2: 0%
    }

    @Test
    void testWarmUp1_CheapestFirst() {
        // Cart: 500, 1200, 800, 300, 950, and 2 copies of 400. Total 7 items.
        List<OrderLine> cart = List.of(
                new OrderLine("1",1, BigDecimal.valueOf(500)),
                new OrderLine("2",1, BigDecimal.valueOf(1200)),
                new OrderLine("3",1, BigDecimal.valueOf(800)),
                new OrderLine("4",1, BigDecimal.valueOf(300)),
                new OrderLine("5",1, BigDecimal.valueOf(950)),
                new OrderLine("6", 2,BigDecimal.valueOf(400))
        );

        List<BigDecimal> discounts = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.CHEAPEST_FIRST);

        // Sorted ascending: 300, 400, 400, 500, 800, 950, 1200
        // Expected discounts: 0, 0, 40.0, 50.0, 120.0, 142.50, 180.0
        assertEquals(7, discounts.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(discounts.get(0)));
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(discounts.get(1)));
        assertEquals(0, BigDecimal.valueOf(40.00).compareTo(discounts.get(2)));
        assertEquals(0, BigDecimal.valueOf(50.00).compareTo(discounts.get(3)));
        assertEquals(0, BigDecimal.valueOf(120.00).compareTo(discounts.get(4)));
        assertEquals(0, BigDecimal.valueOf(142.50).compareTo(discounts.get(5)));
        assertEquals(0, BigDecimal.valueOf(180.00).compareTo(discounts.get(6)));
    }

    @Test
    void testWarmUp1_PriciestFirst() {
        List<OrderLine> cart = List.of(
                new OrderLine("1",1, BigDecimal.valueOf(500)),
                new OrderLine("2",1, BigDecimal.valueOf(1200)),
                new OrderLine("3",1, BigDecimal.valueOf(800)),
                new OrderLine("4",1, BigDecimal.valueOf(300)),
                new OrderLine("5",1, BigDecimal.valueOf(950)),
                new OrderLine("6",2, BigDecimal.valueOf(400))
        );

        List<BigDecimal> discounts = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.PRICIEST_FIRST);

        // Sorted descending: 1200, 950, 800, 500, 400, 400, 300
        // Expected discounts: 0, 0, 80.0, 50.0, 60.0, 60.0, 45.0
        assertEquals(7, discounts.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(discounts.get(0)));
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(discounts.get(1)));
        assertEquals(0, BigDecimal.valueOf(80.00).compareTo(discounts.get(2)));
        assertEquals(0, BigDecimal.valueOf(50.00).compareTo(discounts.get(3)));
        assertEquals(0, BigDecimal.valueOf(60.00).compareTo(discounts.get(4)));
        assertEquals(0, BigDecimal.valueOf(60.00).compareTo(discounts.get(5)));
        assertEquals(0, BigDecimal.valueOf(45.00).compareTo(discounts.get(6)));
    }

    @Test
    void testWarmUp3a_OverlappingTiersRejected() {
        List<Tier> overlappingTiers = List.of(
                new Tier(1, 2, 0),
                new Tier(2, 4, 10) // Overlaps at position 2
        );

        List<OrderLine> cart = List.of(new OrderLine("A",1, BigDecimal.valueOf(100)));

        assertThrows(IllegalArgumentException.class, () -> {
            tieredDiscount.discountPerItem(cart, overlappingTiers, DiscountOrder.CHEAPEST_FIRST);
        });
    }

    @Test
    void testWarmUp3b_GapsMeanZeroPercent() {
        // Tiers: 1-2 (0%), gap at 3-4, 5+ (20%)
        List<Tier> gappedTiers = List.of(
                new Tier(1, 2, 0),
                new Tier(5, null, 20)
        );

        // 5 items priced at 1000 each. Pos 1 & 2 -> 0%, Pos 3 & 4 (gap) -> 0%, Pos 5 -> 20% (200.00)
        List<OrderLine> cart = List.of(new OrderLine("A", 5,BigDecimal.valueOf(1000)));
        List<BigDecimal> result = tieredDiscount.discountPerItem(cart, gappedTiers, DiscountOrder.CHEAPEST_FIRST);

        assertEquals(5, result.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(0)));
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(1)));
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(2))); // Gap item 3
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(3))); // Gap item 4
        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(result.get(4))); // Pos 5 gets 20%
    }

    @Test
    void testUnsortedTiersRejected() {
        List<Tier> unsortedTiers = List.of(
                new Tier(5, null, 15),
                new Tier(1, 2, 0)
        );

        List<OrderLine> cart = List.of(new OrderLine("A",1, BigDecimal.valueOf(100)));

        assertThrows(IllegalArgumentException.class, () -> {
            tieredDiscount.discountPerItem(cart, unsortedTiers, DiscountOrder.PRICIEST_FIRST);
        });
    }

    @Test
    void testCartWithMultipleQuantitiesAndMixedPrices() {
        // Test cart with varying quantities and different prices
        List<OrderLine> cart = List.of(
                new OrderLine("X",3, BigDecimal.valueOf(200)), // [200, 200, 200]
                new OrderLine("Y",1, BigDecimal.valueOf(1000)) // [1000]
        );
        // Total 4 items. Sorted CHEAPEST: 200, 200, 200, 1000
        // Pos 1-2 (0%): 0, 0
        // Pos 3-4 (10%): 200 * 10% = 20, 1000 * 10% = 100
        List<BigDecimal> result = tieredDiscount.discountPerItem(cart, standardTiers, DiscountOrder.CHEAPEST_FIRST);

        assertEquals(4, result.size());
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(0)));
        assertEquals(0, BigDecimal.valueOf(0.00).compareTo(result.get(1)));
        assertEquals(0, BigDecimal.valueOf(20.00).compareTo(result.get(2)));
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(result.get(3)));
    }
}