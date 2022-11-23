package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.BooleanField;
import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.utils.*;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.*;

public class GlobList extends ArrayList<Glob> {
    public static final GlobList EMPTY = new EmptyGlobList();

    public GlobList() {
        super();
    }

    public GlobList(int capacity) {
        super(capacity);
    }

    public GlobList(Glob... globs) {
        this(Arrays.asList(globs));
    }

    public GlobList(GlobList globs) {
        super(globs);
    }

    public GlobList(Collection<Glob> globs) {
        super(globs);
    }

    public void addAll(Glob... globs) {
        for (Glob glob : globs) {
            add(glob);
        }
    }

    public boolean matches(List<Glob> other) {
        return matches(this, other);
    }

    public static boolean matches(List<Glob> globs1, List<Glob> globs2) {
        if (globs1 == globs2) {
            return true;
        } else if (globs1 == null || globs2 == null || globs2.size() != globs1.size()) {
            return false;
        }

        List<Glob> globs2Copy = new ArrayList<>(globs2);
        for (Glob glob1 : globs1) {
            int index = -1;
            for (int i = 0; i < globs2Copy.size(); i++) {
                if (glob1.matches(globs2Copy.get(i))) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return false;
            }

            globs2Copy.remove(index);
        }

        return true;
    }

    public void addNotNull(Glob... globs) {
        for (Glob glob : globs) {
            if (glob != null) {
                add(glob);
            }
        }
    }

    public String toString() {
        List<String> strings = new ArrayList<String>();
        for (Glob glob : this) {
            if (glob == null) {
                strings.add("null");
            }
            else {
                strings.add(glob.toString());
            }
        }
        Collections.sort(strings);
        return strings.toString();
    }

    public GlobList filterSelf(GlobMatcher matcher, GlobRepository repository) {
        removeIf(glob -> !matcher.matches(glob, repository));
        return this;
    }

    public GlobList filter(GlobMatcher matcher, GlobRepository repository) {
        GlobList result = new GlobList();
        for (Glob glob : this) {
            if (matcher.matches(glob, repository)) {
                result.add(glob);
            }
        }
        return result;
    }

    public GlobList getExistingGlobs(GlobRepository repository) {
        GlobList result = new GlobList();
        for (Iterator<Glob> iter = iterator(); iter.hasNext(); ) {
            Glob glob = iter.next();
            if (repository.contains(glob.getKey())) {
                result.add(glob);
            }
        }
        return result;
    }

    public void addAll(Collection<Key> keys, GlobRepository repository) {
        for (Key key : keys) {
            Glob glob = repository.find(key);
            if (glob != null) {
                add(glob);
            }
        }
    }

    public void removeAll(GlobMatcher matcher, GlobRepository repository) {
        for (Iterator<Glob> iter = iterator(); iter.hasNext(); ) {
            Glob glob = iter.next();
            if (matcher.matches(glob, repository)) {
                iter.remove();
            }
        }
    }

    public boolean removeAll(Set<Key> keys) {
        boolean asRemoved = false;
        for (Iterator it = this.iterator(); it.hasNext(); ) {
            Glob glob = (Glob)it.next();
            if (keys.contains(glob.getKey())) {
                it.remove();
                asRemoved = true;
            }
        }
        return asRemoved;
    }

    public GlobList subList(int fromIndex, int toIndex) {
        return new GlobList(super.subList(fromIndex, toIndex));
    }

    public GlobList clone() {
        List copy = new ArrayList(this);
        return new GlobList(copy);
    }

    public Glob[] toArray() {
        return super.toArray(new Glob[size()]);
    }

    public Key[] getKeys() {
        Key[] result = new Key[size()];
        int index = 0;
        for (Glob glob : this) {
            result[index++] = glob.getKey();
        }
        return result;
    }

    public Set<Key> getKeySet() {
        Set<Key> result = new HashSet<Key>();
        for (Glob glob : this) {
            result.add(glob.getKey());
        }
        return result;
    }

    public List<Key> getKeyList() {
        List<Key> result = new ArrayList<Key>(size());
        for (Glob glob : this) {
            result.add(glob.getKey());
        }
        return result;
    }

    public Set<Integer> getValueSet(IntegerField field) {
        Set<Integer> result = new HashSet<Integer>();
        for (Glob glob : this) {
            result.add(glob.get(field));
        }
        return result;
    }

    public Integer[] getValueSetArray(IntegerField field) {
        Set<Integer> result = getValueSet(field);
        return result.toArray(new Integer[result.size()]);
    }

    public Set<Boolean> getValueSet(BooleanField field) {
        Set<Boolean> result = new HashSet<Boolean>();
        for (Glob glob : this) {
            result.add(glob.get(field));
        }
        return result;
    }

    public Set<Double> getValueSet(DoubleField field) {
        Set<Double> result = new HashSet<Double>();
        for (Glob glob : this) {
            result.add(glob.get(field));
        }
        return result;
    }

    public Set<String> getValueSet(StringField field) {
        Set<String> result = new HashSet<String>();
        for (Glob glob : this) {
            result.add(glob.get(field));
        }
        return result;
    }

    public Set getValueSet(Field field) {
        Set result = new HashSet();
        for (Glob glob : this) {
            result.add(glob.getValue(field));
        }
        return result;
    }

    public Double[] getValues(DoubleField field) {
        Double[] result = new Double[size()];
        int index = 0;
        for (Glob glob : this) {
            result[index++] = glob.get(field);
        }
        return result;
    }

    public Integer[] getValues(IntegerField field) {
        Integer[] result = new Integer[size()];
        int index = 0;
        for (Glob glob : this) {
            result[index++] = glob.get(field);
        }
        return result;
    }

    public String[] getValues(StringField field) {
        String[] result = new String[size()];
        int index = 0;
        for (Glob glob : this) {
            result[index++] = glob.get(field);
        }
        return result;
    }

    public SortedSet<Integer> getSortedSet(IntegerField field) {
        SortedSet<Integer> result = new TreeSet<Integer>();
        for (FieldValues values : this) {
            Integer value = values.get(field);
            if (value != null) {
                result.add(value);
            }
        }
        return result;
    }

    public SortedSet<Double> getSortedSet(DoubleField field) {
        SortedSet<Double> result = new TreeSet<Double>();
        for (FieldValues values : this) {
            Double value = values.get(field);
            if (value != null) {
                result.add(value);
            }
        }
        return result;
    }

    public SortedSet<String> getSortedSet(StringField field) {
        SortedSet<String> result = new TreeSet<String>();
        for (FieldValues values : this) {
            String e = values.get(field);
            if (e != null) {
                result.add(e);
            }
        }
        return result;
    }

    public Integer[] getSortedArray(IntegerField field) {
        SortedSet<Integer> result = getSortedSet(field);
        return result.toArray(new Integer[result.size()]);
    }

    public boolean contains(Key key) {
        for (Glob glob : this) {
            if (glob.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(IntegerField field, Integer value) {
        for (Glob glob : this) {
            if (Utils.equal(glob.get(field), value)) {
                return true;
            }
        }
        return false;
    }

    public GlobList sort(Field... field) {
        if (field == null || field.length == 0) {
            return this;
        }
        if (field.length == 1) {
            return sortSelf(new GlobFieldComparator(field[0]));
        }
        return sortSelf(new GlobFieldsComparator(field));
    }

    public GlobList reverseSort(Field field) {
        if (field == null) {
            return this;
        }
        return sortSelf(new ReverseGlobFieldComparator(field));
    }

    public GlobList sortSelf(Comparator<Glob> comparator) {
        Collections.sort(this, comparator);
        return this;
    }

    public GlobList apply(GlobFunctor functor, GlobRepository repository) throws Exception {
        for (Glob glob : this) {
            functor.run(glob, repository);
        }
        return this;
    }

    public GlobList safeApply(GlobFunctor functor, GlobRepository repository) {
        try {
            apply(functor, repository);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState(e);
        }
        return this;
    }

    public List<Key> toKeyList() {
        List<Key> list = new ArrayList<Key>();
        for (Glob glob : this) {
            list.add(glob.getKey());
        }
        return list;
    }

    public Set<GlobType> getTypes() {
        Set<GlobType> types = new HashSet<GlobType>();
        for (Glob glob : this) {
            if (glob != null) {
                types.add(glob.getType());
            }
        }
        return types;
    }

    public Optional<Glob> getFirst() {
        if (isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(get(0));
    }

    public Glob getLast() {
        if (isEmpty()) {
            return null;
        }
        return get(size() - 1);
    }

    public Glob getSingle() {
        return size() == 1 ? get(0) : null;
    }

    public GlobList getAll(GlobType type) {
        GlobList result = new GlobList();
        for (Glob glob : this) {
            if (glob.getType().equals(type)) {
                result.add(glob);
            }
        }
        return result;
    }

    public Map<Integer, Glob> toMap(IntegerField field) {
        Map<Integer, Glob> map = new HashMap<Integer, Glob>();
        for (Glob glob : this) {
            map.put(glob.get(field), glob);
        }
        return map;
    }

    public Integer getMaxValue(IntegerField field) {
        Integer result = null;
        for (Glob glob : this) {
            Integer value = glob.get(field);
            if (result == null) {
                result = value;
            }
            else if ((value != null) && (value > result)) {
                result = value;
            }
        }
        return result;
    }

    public Integer getMinValue(IntegerField field) {
        Integer result = null;
        for (Glob glob : this) {
            Integer value = glob.get(field);
            if (result == null) {
                result = value;
            }
            else if ((value != null) && (value < result)) {
                result = value;
            }
        }
        return result;
    }

    public double getSum(DoubleField field, double defaultForNull) {
        Double result = getSum(field);
        if (result == null) {
            return defaultForNull;
        }
        return result;
    }

    public Double getSum(DoubleField field) {
        Double result = null;
        for (Glob glob : this) {
            Double value = glob.get(field);
            if (result == null) {
                result = value;
            }
            else if (value != null) {
                result += value;
            }
        }
        return result;
    }


    public GlobList getTargets(Link link, GlobRepository repository) {
        GlobList result = new GlobList();
        for (Glob glob : this) {
            Glob target = repository.findLinkTarget(glob, link);
            if ((target != null) && !result.contains(target)) {
                result.add(target);
            }
        }
        return result;
    }

    public Glob find(Key key) {
        if (key == null) {
            return null;
        }
        for (Glob glob : this) {
            if (key.equals(glob.getKey())) {
                return glob;
            }
        }
        return null;
    }
}
