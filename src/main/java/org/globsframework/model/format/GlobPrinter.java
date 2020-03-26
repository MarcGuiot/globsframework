package org.globsframework.model.format;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.GlobArrayField;
import org.globsframework.metamodel.fields.GlobArrayUnionField;
import org.globsframework.metamodel.fields.GlobField;
import org.globsframework.metamodel.fields.GlobUnionField;
import org.globsframework.metamodel.utils.GlobTypeComparator;
import org.globsframework.model.*;
import org.globsframework.utils.Strings;
import org.globsframework.utils.TablePrinter;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

import static org.globsframework.utils.Utils.sort;

public class GlobPrinter {

    public static String toString(final FieldsValueScanner fieldValues) {
        final StringBuilder builder = new StringBuilder();
        if (fieldValues == null) {
            return "NULL";
        }
        fieldValues.safeApply((field, value) ->
                builder
                        .append(field.getName())
                        .append("=")
                        .append(field.toString(value, ""))
                        .append('\n'));
        return builder.toString();
    }

    public static void print(GlobRepository repository, GlobType... types) {
        GlobPrinter.init(repository).showOnly(types).run();
    }

    public static void print(GlobList list) {
        GlobPrinter.init(list).run();
    }

    public static GlobPrinter init(GlobRepository repository) {
        return new GlobPrinter(repository);
    }

    public static GlobPrinter init(GlobList list) {
        return new GlobPrinter(list);
    }

    public static GlobPrinter init(Glob glob) {
        GlobList list = new GlobList();
        list.add(glob);
        return new GlobPrinter(list);
    }

    public static void print(Glob glob) {
        print(glob, new OutputStreamWriter(System.out));
    }

    public static void print(Glob glob, Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        printer.println("===== " + glob + " ======");

        List<Object[]> rows = new ArrayList<Object[]>();
        for (Field field : glob.getType().getFields()) {
            rows.add(new Object[]{field.getName(), glob.getValue(field)});
        }

        TablePrinter.print(new String[]{"Field", "Value"}, rows, true, printer);

        printer.println();
        printer.flush();
    }

    private GlobRepository repository;
    private Set<GlobType> types;
    private GlobList globs;
    private String[] filters;
    private List<Field> excludedFields = new ArrayList<Field>();
    private Map<GlobType, Field[]> fieldsForType = new HashMap<GlobType, Field[]>();

    private GlobPrinter(GlobRepository repository) {
        this.repository = repository;
        this.types = repository.getTypes();
        this.globs = repository.getAll();
    }

    private GlobPrinter(GlobList list) {
        this.types = list.getTypes();
        this.globs = list;
    }

    public GlobPrinter showOnly(GlobType... shownTypes) {
        if (shownTypes.length > 0) {
            this.types = new HashSet<GlobType>(Arrays.asList(shownTypes));
        }
        return this;
    }

    public GlobPrinter setTextFilters(String... textFilters) {
        this.filters = textFilters;
        return this;
    }

    public GlobPrinter showFields(GlobType type, Field... fields) {
        fieldsForType.put(type, fields);
        return this;
    }

    public GlobPrinter exclude(Field... fields) {
        this.excludedFields.addAll(Arrays.asList(fields));
        return this;
    }

    public void run(Writer writer) {
        PrintWriter printer = new PrintWriter(writer);
        for (GlobType type : sort(types, GlobTypeComparator.INSTANCE)) {
            printType(type, globs.getAll(type), printer);
        }
    }

    public void run() {
        run(new OutputStreamWriter(System.out));
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        run(writer);
        return writer.toString();
    }

    private void printType(GlobType type, GlobList globs, PrintWriter printer) {
        printer.println("===== " + type.getName() + " ======");

        List<Object[]> rows = new ArrayList<Object[]>();
        String[] headerRow = createHeaderRow(type);
        for (Glob glob : globs) {
            String[] row = createRow(type, glob);
            if (filters != null) {
                boolean found = false;
                for (int i = 0; i < row.length && !found; i++) {
                    for (int j = 0; j < filters.length && !found; j++) {
                        if (row[i].contains(filters[j])) {
                            found = true;
                        }
                    }
                }
                if (found) {
                    rows.add(row);
                }
            } else {
                rows.add(row);
            }
        }

        TablePrinter.print(headerRow, rows, true, printer);

        printer.println();
        printer.flush();
    }

    private String[] createRow(GlobType type, Glob glob) {
        List<String> row = new ArrayList<String>();
        for (Field field : getFields(type)) {
            if (excludedFields.contains(field)) {
                continue;
            }
            row.add(getValue(glob, field, glob.getValue(field)));
        }
        return row.toArray(new String[row.size()]);
    }

    private Field[] getFields(GlobType type) {
        Field[] fields = fieldsForType.get(type);
        return fields != null ? fields : type.getFields();
    }

    private String getValue(Glob glob, Field field, Object value) {
        if (value == null) {
            return "";
        }
        if (field instanceof GlobField || field instanceof GlobUnionField) {
            return Arrays.toString(createRow(((Glob) value).getType(), ((Glob) value)));
        } else if (field instanceof GlobArrayField || field instanceof GlobArrayUnionField) {
            StringBuilder builder = new StringBuilder();
            for (Glob globChild : ((Glob[]) value)) {
                builder.append(Arrays.toString(createRow(globChild.getType(), globChild)));
                builder.append(",");
            }
            return builder.toString();
        }
        return Strings.toString(value);
    }

    private String[] createHeaderRow(GlobType type) {
        List<String> row = new ArrayList<String>();
        for (Field field : getFields(type)) {
            if (excludedFields.contains(field)) {
                continue;
            }
            row.add(field.getName());
        }
        return row.toArray(new String[row.size()]);
    }
}

