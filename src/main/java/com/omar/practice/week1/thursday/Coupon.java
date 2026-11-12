package com.omar.practice.week1.thursday;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Coupon {
    public String code;
    public String type; // "PERCENT", "FIXED", "FREESHIP"
    public BigDecimal value; // 15 means 15% for PERCENT, or 15.00 off for FIXED
    public LocalDate expires;
}

//bug 1 -> type is string, it has to be enum
//bug 2 -> expires is LocalDate, it has to be LocalDateTime so that expiry can be more accurate (precisely on seconds)
