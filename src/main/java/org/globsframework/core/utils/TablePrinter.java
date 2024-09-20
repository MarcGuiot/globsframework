package org.globsframework.core.utils;

import org.globsframework.core.utils.exceptions.InvalidParameter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TablePrinter {

    private Object[] header;
    private List<Object[]> rows = new ArrayList<Object[]>();
    private boolean sorted;

    public TablePrinter() {
        this(false);
    }

    public TablePrinter(boolean sorted) {
        this.sorted = sorted;
    }

    public void setHeader(Object... titles) {
        header = titles;
    }

    public void addRow(Object... items) {
        rows.add(items);
    }

    public void print(PrintWriter printer) {
        if (header == null) {
            if (rows.isEmpty()) {
                return;
            }
        }
        print(header, rows, sorted, printer);
    }

    public void print() {
        print(new PrintWriter(System.out));
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        print(new PrintWriter(writer));
        return writer.toString();
    }

    public static String toString(Object[] headerRow, List<Object[]> rows) {
        StringWriter writer = new StringWriter();
        print(headerRow, rows, true, new PrintWriter(writer));
        return writer.toString();
    }

    public static void print(Object[] headerRow, List<Object[]> rows, boolean sorted, PrintWriter printer) {
        List<Object[]> aggregated = new ArrayList<Object[]>();
        if (headerRow != null) {
            aggregated.add(headerRow);
        }
        aggregated.addAll(rows);
        if (aggregated.isEmpty()) {
            return;
        }

        Object[] firstRow = aggregated.get(0);
        int[] sizes = new int[firstRow.length];
        Arrays.fill(sizes, 0);

        for (Object[] row : aggregated) {
            if (row.length > firstRow.length) {
                throw new InvalidParameter("Row larger than the first row: " + Arrays.toString(row));
            }
        }

        for (Object[] row : aggregated) {
            updateSizes(row, sizes);
        }

        if (headerRow != null) {
            printer.println(toString(headerRow, sizes));
        }
        List<String> strings = new ArrayList<String>();
        for (Object[] row : rows) {
            strings.add(toString(row, sizes));
        }
        if (sorted) {
            strings = Utils.sort(strings);
        }
        for (String string : strings) {
            printer.println(string);
        }

        printer.flush();
    }

    private static void updateSizes(Object[] row, int[] sizes) {
        int index = 0;
        for (Object cell : row) {
            String str = toString(cell);
            sizes[index] = Math.max(sizes[index], str.length());
            index++;
        }
    }

    private static String toString(Object[] row, int[] sizes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            builder.append("| ");
            String string = toString(row[i]);
            builder.append(string);
            int space = sizes[i] - string.length();
            for (int j = 0; j < space; j++) {
                builder.append(' ');
            }
            builder.append(' ');
        }
        builder.append('|');
        return builder.toString();
    }

    private static String toString(Object cell) {
        if (cell == null) {
            return "";
        }
        if (cell instanceof Double) {
            return new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US)).format(cell);
        }
        return cell.toString();
    }
}
