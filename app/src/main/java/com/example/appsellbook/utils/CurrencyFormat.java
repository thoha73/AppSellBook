package com.example.appsellbook.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
    public static String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        return (formatter.format(amount));
    }
}
