package com.omar.practice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter

    public class NewCustomer {
        private final String name;
        private final String street;
        private final String city;
        private final String postcode;
        private final Optional<String> phone;
        private final Set<String> tags;

        public NewCustomer(
                String name,
                String street,
                String city,
                String postcode,
                Optional<String> phone,
                Set<String> tags
        ) {
            this.name = checkNotNull(name);
            this.street = checkNotNull(street);
            this.city = checkNotNull(city);
            this.postcode = checkNotNull(postcode);
            this.phone = Objects.requireNonNull(phone, "phone cannot be null");
            this.tags = Set.copyOf(Objects.requireNonNull(tags, "tags cannot be null"));
        }

        public NewCustomer convert(LegacyCustomer c) {
            if (c == null) {
                throw new IllegalArgumentException("LegacyCustomer cannot be null");
            }

            Optional<String> phone = Optional.ofNullable(c.phone);

            Set<String> tags = c.tags == null
                    ? Set.of()
                    : c.tags.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            return new NewCustomer(
                    c.name,
                    c.street,
                    c.city,
                    c.postcode,
                    phone,
                    tags
            );
        }

        private String checkNotNull(String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("cannot be null or blank");
            }
            return value;
        }
    }
