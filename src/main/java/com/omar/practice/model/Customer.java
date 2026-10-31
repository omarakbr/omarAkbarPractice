package com.omar.practice.model;

import java.util.UUID;


    public record Customer(UUID id, String name, String email, boolean member,
                           boolean banned) {}

