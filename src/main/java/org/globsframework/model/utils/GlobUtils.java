package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.BooleanField;
import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.*;
import org.globsframework.utils.Utils;

import java.util.*;

public class GlobUtils {

    public static GlobType[] toArray(Collection<GlobType> types) {
        return types.toArray(new GlobType[types.size()]);
    }

    public static boolean safeIsTrue(Glob glob, BooleanField field) {
        if (glob == null) {
            return false;
        }
        return glob.isTrue(field);
    }

    public static Boolean safeGet(Glob glob, BooleanField field) {
        if (glob == null) {
            return null;
        }
        return glob.get(field);
    }

    public static String safeGet(Glob glob, StringField field) {
        if (glob == null) {
            return null;
        }
        return glob.get(field);
    }

    public static Double safeGet(Glob glob, DoubleField field) {
        if (glob == null) {
            return null;
        }
        return glob.get(field);
    }

    public static SortedSet<Integer> getSortedValues(FieldValues[] valuesList, IntegerField field) {
        SortedSet<Integer> result = new TreeSet<Integer>();
        for (FieldValues values : valuesList) {
            result.add(values.get(field));
        }
        return result;
    }

    public static Set<Key> createKeys(GlobType type, Set<Integer> ids) {
        Set<Key> keys = new HashSet<Key>();
        for (Integer id : ids) {
            keys.add(KeyBuilder.newKey(type, id));
        }
        return keys;
    }

    public static Set<GlobType> getTypes(Collection<Key> keys) {
        Set<GlobType> result = new HashSet<GlobType>();
        for (Key key : keys) {
            result.add(key.getGlobType());
        }
        return result;
    }

    public static Set<Integer> getValues(Collection<Key> keys, IntegerField field) {
        Set<Integer> result = new HashSet<Integer>();
        for (Key key : keys) {
            result.add(key.get(field));
        }
        return result;
    }

    public static void setValue(Glob glob, Double value, GlobRepository repository, DoubleField... fields) {
        repository.startChangeSet();
        try {
            for (DoubleField field : fields) {
                repository.update(glob.getKey(), field, value);
            }
        }
        finally {
            repository.completeChangeSet();
        }
    }

    public static String toString(Glob[] globs) {
        List<String> strings = new ArrayList<String>();
        for (Glob glob : globs) {
            strings.add(glob.getKey().toString());
        }
        Collections.sort(strings);
        return strings.toString();
    }

    public static void add(Glob glob, DoubleField field, double value, GlobRepository repository) {
        Key key = glob.getKey();
        add(key, glob, field, value, repository);
    }

    public static void add(Key key, Glob glob, DoubleField field, double value, GlobRepository repository) {
        Double currentValue = glob.get(field);
        if (currentValue == null) {
            repository.update(key, field, value);
            return;
        }
        if (value != 0) {
            double newValue = currentValue + value;
            repository.update(key, field, newValue);
        }
    }

    public static GlobList getUniqueTargets(GlobList from, Link link, GlobRepository repository) {
        GlobList result = new GlobList();
        Set<Glob> added = new HashSet<Glob>();
        for (Glob glob : from) {
            Glob target = repository.findLinkTarget(glob, link);
            if (target != null && added.add(target)) {
                result.add(target);
            }
        }
        return result;
    }

    public static void updateIfExists(GlobRepository repository, Key key, Field field, Object value) {
        final Glob glob = repository.find(key);
        if (glob != null) {
            repository.update(key, field, value);
        }
    }

    public static void copy(GlobRepository repository, Glob from, Glob to, Field... fields) {
        FieldValue fieldValues[] = new FieldValue[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            fieldValues[i] = new FieldValue(field, from.getValue(field));
        }
        repository.update(to.getKey(), fieldValues);
    }

    public static GlobList getAll(Set<Key> keys, GlobRepository repository) {
        GlobList result = new GlobList();
        for (Key key : keys) {
            result.add(repository.get(key));
        }
        return result;
    }

    public interface DiffFunctor<T> {
        void add(T glob, int index);

        void remove(int index);

        void move(int previousIndex, int newIndex);
    }

    private static Object NULL = new Object();

    public static <T> void diff(List<T> from, List<T> to, DiffFunctor<T> functor) {
        Object[] fromArray = from.toArray(new Object[from.size() + to.size()]);
        for (int i1 = from.size(); i1 < fromArray.length; i1++) {
            fromArray[i1] = NULL;
        }
        int toPos = 0;

        Object fromT = fromArray.length == 0 ? null : fromArray[0];
        for (T element : to) {
            if (!Utils.equal(element, fromT)) {
                boolean moved = false;
                for (int i = toPos + 1; i < fromArray.length; i++) {
                    Object t = fromArray[i];
                    if (t != NULL && t != null && t.equals(element)) {
                        functor.move(i, toPos);
                        System.arraycopy(fromArray, toPos, fromArray, toPos + 1, i - toPos);
                        fromArray[toPos] = t;
                        moved = true;
                        break;
                    }
                }

                if (!moved) {
                    functor.add(element, toPos);
                    System.arraycopy(fromArray, toPos, fromArray, toPos + 1, fromArray.length - toPos - 1);
                    fromArray[toPos] = element;
                }
            }
            toPos++;
            if (toPos < fromArray.length) {
                fromT = fromArray[toPos];
            }
            else {
                fromT = null;
            }
        }
        for (int index = fromArray.length; index > to.size(); index--) {
            if (fromArray[index - 1] != NULL) {
                functor.remove(index - 1);
            }
        }
    }
}
