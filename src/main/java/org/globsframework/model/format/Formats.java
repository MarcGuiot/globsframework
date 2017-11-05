package org.globsframework.model.format;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Formats {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final DecimalFormat DEFAULT_DECIMAL_FORMAT =
        new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US));
    public static final String DEFAULT_YES_VALUE = "yes";
    public static final String DEFAULT_NO_VALUE = "no";

    public static final Formats DEFAULT = new Formats();

    private DateFormat timestampFormat;
    private DateFormat dateFormat;
    private DecimalFormat decimalFormat;
    private String valueForTrue;
    private String valueForFalse;

    public Formats() {
        this(DEFAULT_DATE_FORMAT, DEFAULT_TIMESTAMP_FORMAT, DEFAULT_DECIMAL_FORMAT, DEFAULT_YES_VALUE, DEFAULT_NO_VALUE);
    }

    public Formats(DateFormat dateFormat, DateFormat timestampFormat, DecimalFormat decimalFormat, String valueForTrue, String valueForFalse) {
        this.timestampFormat = timestampFormat;
        this.dateFormat = dateFormat;
        this.decimalFormat = decimalFormat;
        this.valueForTrue = valueForTrue;
        this.valueForFalse = valueForFalse;
    }

    public DateFormat getTimestampFormat() {
        return timestampFormat;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public String convertToString(boolean value) {
        return value ? valueForTrue : valueForFalse;
    }
}
