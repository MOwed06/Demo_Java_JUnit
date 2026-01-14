package demo.mowed.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class StringHelper {
    private static final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

    public static String toCurrency(float value) {
        return formatter.format(value);
    }
}
