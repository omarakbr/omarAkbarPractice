package com.omar.practice.week1.monday;

import com.omar.practice.model.Book;
import com.omar.practice.model.Order;
import com.omar.practice.model.OrderStatus;

import java.util.*;

public class BestsellerReport {
    /** Copies sold per ISBN. Count only orders that are PAID, SHIPPED or
     DELIVERED. */
    public Map<String, Integer> copiesSold(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return Map.of();
        }

        List<OrderStatus> requiredStatuses = List.of(
                OrderStatus.PAID,
                OrderStatus.SHIPPED,
                OrderStatus.DELIVERED
        );

        Map<String, Integer> sold = new HashMap<>();

//        filtering the rewuired statuses first then finding sold ones. and then merging.
        orders.stream().filter(order -> requiredStatuses.contains(order.status())).map(Order::lines)
                .forEach(lines -> {
                    lines.forEach(line -> {
                        if (line != null && line.isbn() != null) {
                            sold.merge(line.isbn(), line.quantity(), Integer::sum);
                        }
                    });
                });

        return sold;
    }
    /** Top n books by copies sold. Ties: title A→Z. A book missing from the
     catalog is skipped. */
    public List<Book> topN(List<Order> orders, Map<String, Book> catalog, int
            n) {
//        book1 -> 5 sold
//        book2 -> 4 sold
//        book3 -> 3 sold
//        book4 -> 2 sold
//        book5 -> 1 sold


        throw new UnsupportedOperationException(); // TODO
    }
    /** Categories with zero copies sold, A→Z. */
    public List<String> deadCategories(List<Order> orders, List<Book> catalog)
    {
//        order -> orderLines -> book ka isbn.
//        bookIsbn -> search in catalog -> matched and set the category in orderedCategories. in the end we check difference
        Set<String> orderedBookIsbns = new HashSet<>();
        Set<String> orderedCategories = new HashSet<>();
        Set<String> allCategories = new HashSet<>();
        List<String> deadCategories = new ArrayList<>();

        // getting list of all ordered books
         orders.stream().map(Order::lines).forEach(lines -> {
             lines.forEach(line -> orderedBookIsbns.add(line.isbn()));
         });

//         getting list of all ordered books categories
         for (String isbn : orderedBookIsbns) {
             for (Book book : catalog) {
                 if (book != null && book.isbn() != null && book.isbn().equals(isbn)) {
                     orderedCategories.add(book.category());
                 }
             }
         }

//         finding all categories and filtering that are not ordered
            catalog.forEach(book -> allCategories.add(book.category()));
            allCategories.forEach( category -> {
                if (!orderedCategories.contains(category)) {
                    deadCategories.add(category);
                }
            });

         //        throw new UnsupportedOperationException(); // TODO
            return deadCategories;
}

}
