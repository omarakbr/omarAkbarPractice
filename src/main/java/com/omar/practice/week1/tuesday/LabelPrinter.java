package com.omar.practice.week1.tuesday;

import com.omar.practice.model.LegacyCustomer;
import com.omar.practice.model.NewCustomer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LabelPrinter {

    private final NewCustomer newCustomer;

    public String print(LegacyCustomer legacyCustomer) {
        NewCustomer convertedData = newCustomer.convert(legacyCustomer);
        return formatLabel(convertedData);
    }

    private String formatLabel(NewCustomer customer){
        StringBuilder label = new StringBuilder();

        label.append(customer.getName().toUpperCase()).append("\n");
        label.append(customer.getStreet()).append("\n");
        label.append(customer.getCity()).append(" ").append(customer.getPostcode());

        customer.getPhone().ifPresent(phone ->
                label.append("\nTel: ").append(phone)
        );

        return label.toString();
    }
}
