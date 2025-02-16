package com.restaurant.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formatter {

    private Formatter() {
    }

    public static String prettyCurrency(long amount) {
        double amountInDollars = amount / 100.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(amountInDollars);
    }

    public static String prettyDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM, yyyy hh:mm a");
        return dateTime.format(formatter).toLowerCase();
    }

    public static String prettyPercent(double value) {
        DecimalFormat percentageFormat = new DecimalFormat("##.##%");
        return percentageFormat.format(value);
    }
}
