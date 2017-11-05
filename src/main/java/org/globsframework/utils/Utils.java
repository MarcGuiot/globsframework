package org.globsframework.utils;

import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.lang.reflect.Array;
import java.util.*;

public class Utils {
    private Utils() {
    }

    public static boolean equal(Object o1, Object o2) {
        return (o1 == null) && (o2 == null) || !((o1 == null) || (o2 == null)) && o1.equals(o2);
    }

    public static boolean equalIgnoreCase(String o1, String o2) {
        if ((o1 == null) && (o2 == null)) {
            return true;
        }
        if ((o1 == null) || (o2 == null)) {
            return false;
        }
        return o1.equalsIgnoreCase(o2);
    }

    public static <T> int compare(Comparable<T> o1, T o2) {
        if ((o1 == null) && (o2 == null)) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return o1.compareTo(o2);
    }

    public static <T> int reverseCompare(Comparable<T> o1, T o2) {
        return compare(o1, o2) * -1;
    }

    public static int compareIgnoreCase(String o1, String o2) {
        if ((o1 == null) && (o2 == null)) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        return o1.toLowerCase().compareTo(o2.toLowerCase());
    }

    public static <T> List<T> toList(Iterator<T> iterator) {
        List<T> list = new ArrayList<T>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public static Double zeroIfNull(Double value) {
        return value != null ? value : 0.;
    }

    public static Integer defaultIfNull(Integer value, int def) {
        return value != null ? value : def;
    }

    public static <T extends Comparable> T min(Collection<T> values) {
        return minOrMax(-1, values);
    }

    public static <T extends Comparable> T max(Collection<T> values) {
        return minOrMax(1, values);
    }

    private static <T extends Comparable> T minOrMax(int sign, Collection<T> values) {
        if (values.isEmpty()) {
            return null;
        }
        T result = values.iterator().next();
        for (T value : values) {
            if (value == null) {
                continue;
            }
            if ((result == null) || (result.compareTo(value) * sign < 0)) {
                result = value;
            }
        }
        return result;
    }

    public static <T extends Comparable> T min(T... values) {
        return minOrMax(-1, values);
    }

    public static <T extends Comparable> T max(T... values) {
        return minOrMax(1, values);
    }

    private static <T extends Comparable> T minOrMax(int sign, T... values) {
        if (values.length == 0) {
            return null;
        }
        T result = values[0];
        for (T value : values) {
            if (value == null) {
                continue;
            }
            if ((result == null) || (result.compareTo(value) * sign < 0)) {
                result = value;
            }
        }
        return result;
    }

    public static int minInt(int... values) {
        return minOrMax(-1, values);
    }

    public static int maxInt(int... values) {
        return minOrMax(1, values);
    }

    private static int minOrMax(int sign, int... values) {
        if (values.length == 0) {
            return 0;
        }
        int result = values[0];
        for (int value : values) {
            if ((result - value) * sign < 0) {
                result = value;
            }
        }
        return result;
    }

    public static int randomInt(int max) {
        return (int)Math.round(Math.random() * max);
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static String[] join(String[] first, String[] second) {
        String[] result = new String[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String[] join(String first, String[] other) {
        String[] result = new String[other.length + 1];
        result[0] = first;
        System.arraycopy(other, 0, result, 1, other.length);
        return result;
    }

    public static int[] join(int first, int[] other) {
        int[] result = new int[other.length + 1];
        result[0] = first;
        System.arraycopy(other, 0, result, 1, other.length);
        return result;
    }

    public static <T> List<T> list(T[] objs) {
        return Arrays.asList(objs);
    }

    public static <T> List<T> list(T first, T... other) {
        List<T> result = new ArrayList<T>();
        result.add(first);
        for (T t : other) {
            result.add(t);
        }
        return result;
    }

    public static <T> Set<T> set(T[] array) {
        Set<T> result = new HashSet<T>();
        for (T value : array) {
            result.add(value);
        }
        return result;
    }

    public static <T> Set<T> set(T first, T... other) {
        Set<T> result = new HashSet<T>();
        result.add(first);
        for (T t : other) {
            result.add(t);
        }
        return result;
    }

    public static <T> List<T> sort(Set<T> list, Comparator<T> comparator) {
        List<T> result = new ArrayList<T>(list);
        return sort(result, comparator);
    }

    public static <T extends Comparable> List<T> sort(List<T> list) {
        Collections.sort(list);
        return list;
    }

    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
        return list;
    }

    public static Integer[] toObjectIntegers(int[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static int[] toArray(Collection<Integer> result) {
        int[] array = new int[result.size()];
        int index = 0;
        for (Integer month : result) {
            array[index++] = month;
        }
        return array;
    }

    public static String[] remove(String[] initial, String... toExclude) {
        List<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(initial));
        result.removeAll(Arrays.asList(toExclude));
        return result.toArray(new String[result.size()]);
    }

    public static int[] append(int[] array1, int... array2) {
        int[] result = new int[array1.length + array2.length];
        int index = 0;
        for (int i : array1) {
            result[index++] = i;
        }
        for (int i : array2) {
            result[index++] = i;
        }
        return result;
    }

    public static double sum(Double[] values) {
        double result = 0;
        for (Double value : values) {
            if (value != null) {
                result += value;
            }
        }
        return result;
    }

    public static Integer[] range(int min, int max) {
        if (min > max) {
            throw new InvalidParameter("Lower bound " + min + " should be less than " + max);
        }
        Integer[] result = new Integer[max - min + 1];
        for (int i = min; i <= max; i++) {
            result[i - min] = i;
        }
        return result;
    }

    public static int[] intRange(int min, int max) {
        if (min > max) {
            throw new InvalidParameter("Lower bound " + min + " should be less than " + max);
        }
        int[] result = new int[max - min + 1];
        for (int i = min; i <= max; i++) {
            result[i - min] = i;
        }
        return result;
    }

    public static <T extends Enum<T>> T toEnum(Class<T> enumClass, String name) throws ItemNotFound {
        if (name == null) {
            return null;
        }
        T result = T.valueOf(enumClass, toNiceUpperCase(name.trim()));
        if (result == null) {
            throw new ItemNotFound("No enum " + enumClass.getSimpleName() + " found for value: " + name);
        }
        return result;
    }

    public static void beginRemove() {
    }

    public static void endRemove() {
    }

    public static void throwException(RuntimeException exception) {
        throw exception;
    }

    public static void dumpStack() {
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (StackTraceElement[] elements : map.values()) {
            System.err.println("dumpStack ------------------------------------------");
            for (StackTraceElement element : elements) {
                System.err.println("  at " + element.getClassName() + "." + element.getMethodName()
                                   + "(" + element.getFileName() + ":" + element.getLineNumber() + ')');
            }
        }
    }

    public static <T> List<T> joinedList(T first, T... others) {
        ArrayList<T> list = new ArrayList<T>();
        list.add(first);
        Collections.addAll(list, others);
        return list;
    }

    public static <T> List<List<T>> split(List<T> list, int count) {
        if (count <= 0) {
            throw new InvalidParameter("Parameter 'count' must be > 0 - actual: " + count);
        }

        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int pos = 0;
        while (pos < list.size()) {
            int nextPos = Math.min(pos + count, list.size());
            result.add(list.subList(pos, nextPos));
            pos = nextPos;
        }
        return result;
    }

    public static String toNiceUpperCase(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if ((i > 0) && Character.isUpperCase(c) && Character.isLowerCase(value.charAt(i - 1))) {
                builder.append("_");
            }
            builder.append(Character.toUpperCase(c));
        }
        return builder.toString();
    }

    public static String capitalize(String value) {
        if ((value == null) || "".equals(value)) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
    }


    // code from java 1.6
    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[])copyOf(original, newLength, original.getClass());
    }

    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = ((Object)newType == (Object)Object[].class)
                   ? (T[])new Object[newLength]
                   : (T[])Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }

    public static Integer[] box(int[] array) {
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public interface MapAdapter<KEY, VALUE, D> {

        KEY getKey(D d);

        VALUE getValue(D d);
    }

    public static <KEY, VALUE, D> Map<KEY, VALUE> adapt(D[] ds, MapAdapter<KEY, VALUE, D> mapAdapter) {
        Map<KEY, VALUE> map = new HashMap<KEY, VALUE>(ds.length);
        for (D d : ds) {
            map.put(mapAdapter.getKey(d), mapAdapter.getValue(d));
        }
        return map;
    }
}
