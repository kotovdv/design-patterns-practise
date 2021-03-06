package com.kotovdv.abstractfactory.selector.impl;

import com.kotovdv.abstractfactory.range.Range;
import com.kotovdv.abstractfactory.selector.Selector;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Dmitriy Kotov
 */
public class RandomLocalDateSelector implements Selector<LocalDate> {

    @Override
    public LocalDate select(Range<LocalDate> range) {
        LocalDate from = range.from();
        LocalDate to = range.to();
        long daysBetween = getDaysBetween(from, to);

        return from.plusDays(ThreadLocalRandom.current().nextLong(daysBetween));
    }

    private long getDaysBetween(LocalDate from, LocalDate to) {
        return DAYS.between(from, to);
    }
}
