package com.omar.practice.week2.tuesay;

import com.omar.practice.model.DiscountOrder;
import com.omar.practice.model.OrderLine;
import com.omar.practice.model.Tier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TieredDiscount {
    /**
     * Each OrderLine with quantity 3 counts as 3 separate items.
     * Returns the discount amount for every individual item, in the order you
     sorted them.
     */
    public List<BigDecimal> discountPerItem(List<OrderLine> cart, List<Tier>
            tiers, DiscountOrder order) {
        //extract all the prices
        List<BigDecimal> itemPrices = new ArrayList<>();
        if (cart != null) {
            for (OrderLine line : cart) {
                for (int i = 0; i < line.quantity(); i++) {
                    itemPrices.add(line.unitPrice());
                }
            }
        }

        // sort based on input order
        if (order == DiscountOrder.CHEAPEST_FIRST) {
            itemPrices.sort(Comparator.naturalOrder());
        } else {
            itemPrices.sort(Comparator.reverseOrder());
        }

        List<BigDecimal> discounts = new ArrayList<>();
        for (int i = 0; i < itemPrices.size(); i++) {
            int position = i + 1;
            BigDecimal price = itemPrices.get(i);
            int discountPercent = findPercentForPosition(tiers, position);

            BigDecimal discountAmount = price
                    .multiply(BigDecimal.valueOf(discountPercent))
                    .divide(BigDecimal.valueOf(100));

            discounts.add(discountAmount);
        }

        return discounts;
    }

    private int findPercentForPosition(List<Tier> tiers, int position) {
        for (Tier tier : tiers) {
            boolean matchesFrom = position >= tier.fromItem();
            boolean matchesTo = (tier.toItem() == null) || (position <= tier.toItem());
            if (matchesFrom && matchesTo) {
                return tier.percent();
            }
        }
        return 0;
    }

    /** Reject tier lists that overlap or are not sorted. Gaps mean 0% for the
     missing positions. */
    void validate(List<Tier> tiers) {

        for (int i = 0; i < tiers.size(); i++) {
            Tier current = tiers.get(i);

            // 1. Check if fromItem is greater than toItem
            if (current.fromItem() > current.toItem()) {
                throw new IllegalArgumentException("fromItem cannot be greater than toItem");
            }

            // 2. Check against the next tier (if it exists)
            if (i < tiers.size() - 1) {
                Tier next = tiers.get(i + 1);

                // Check if tiers are not sorted properly
                if (current.fromItem() >= next.fromItem()) {
                    throw new IllegalArgumentException("Tiers must be sorted by fromItem");
                }

                // Check if tiers overlap (e.g., 1-2 and 2-4)
                if (current.toItem() >= next.fromItem()) {
                    throw new IllegalArgumentException("Tier ranges cannot overlap.");
                }
            }
        }
    }
}
