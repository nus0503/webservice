package com.jojoldu.book.webservice.common.dateTime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    public static long calculateTimeUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.truncatedTo(ChronoUnit.DAYS).plusDays(1);
        return ChronoUnit.SECONDS.between(now, midnight);
    }
}
