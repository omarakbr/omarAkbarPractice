package com.omar.practice.week2.thursday.service;

import java.util.*;

public class DiscountService {

//    (incomplete for now)

    // customers that have already used the welcome discount (coming from db)
    private final Set<UUID> welcomeDiscountClaimedUsers = new HashSet<>();

    public boolean hasUsedWelcomeDiscount(UUID customerId) {
        return welcomeDiscountClaimedUsers.contains(customerId);
    }
}