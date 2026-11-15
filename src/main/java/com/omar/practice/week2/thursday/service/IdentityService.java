package com.omar.practice.week2.thursday.service;

import com.omar.practice.model.Customer;
import java.util.*;

public class IdentityService {

    // previously used payment/phone of customer coming from db
    private final Map<UUID,List<String>> usedCreditCardFingerprints = new HashMap<>();
    private final Map<UUID,List<String>> usedPhoneNumbers = new HashMap<>();
    private final DiscountService discountService = new DiscountService();

    public boolean hasFingerprintBeenUsed(Customer customer) {
        boolean cardUsed = usedCreditCardFingerprints.containsKey(customer.id());
        boolean phoneUsed = usedPhoneNumbers.containsKey(customer.id());

        return cardUsed || phoneUsed;
    }

    public boolean isEligibleForWelcome(Customer customer) {
        // 1. Check if user (card/phone) already used it
        if (!discountService.hasUsedWelcomeDiscount(customer.id()) && !hasFingerprintBeenUsed(customer)) {
            return true;
        }
        return false;
    }
}
