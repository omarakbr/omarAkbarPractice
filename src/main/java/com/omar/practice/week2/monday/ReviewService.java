package com.omar.practice.week2.monday;


import com.omar.practice.model.*;

import java.util.Objects;

public class ReviewService {


    public boolean canReview(Customer c, Order o, Book b) {
        return isEligibleStatus(c) && hadBookDelivered(o) && boughBook(o,b);
    }

    private boolean isEligibleStatus(Customer c){
        return c.member() && !c.banned();
    }

    private boolean boughBook(Order o,Book b){
        for (OrderLine line : o.lines()) {
            if (Objects.equals(line.isbn(), b.isbn())){
                return true;
            }
        }
        return false;
    }

    private boolean hadBookDelivered(Order o){
        return o.status() == OrderStatus.SHIPPED || o.status() == OrderStatus.DELIVERED;
    }


}
