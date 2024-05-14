package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.links.impl.DefaultMutableGlobLinkModel;
import org.globsframework.utils.Strings;
import org.globsframework.utils.exceptions.InvalidData;

import java.util.*;

public class GlobTypeDependencies {

    private List<GlobType> createSequence = new ArrayList<GlobType>();
    private List<GlobType> deleteSequence = new ArrayList<GlobType>();
    private Set<GlobType> postUpdate = new HashSet<GlobType>();

    public GlobTypeDependencies(Collection<GlobType> types, DefaultMutableGlobLinkModel globLinkModel) {
        dispatch(globLinkModel, types);

        deleteSequence = new ArrayList<GlobType>(createSequence);
        Collections.reverse(deleteSequence);
    }

    public List<GlobType> getCreationSequence() {
        return Collections.unmodifiableList(createSequence);
    }

    public List<GlobType> getUpdateSequence() {
        return Collections.unmodifiableList(createSequence);
    }

    public List<GlobType> getDeletionSequence() {
        return Collections.unmodifiableList(deleteSequence);
    }

    public boolean needsPostUpdate(GlobType type) {
        return postUpdate.contains(type);
    }

    private void dispatch(DefaultMutableGlobLinkModel globLinkModel, Collection<GlobType> types) {
        Set<GlobType> done = new HashSet<GlobType>();
        List<GlobType> typeList = new ArrayList<GlobType>(types);
        Collections.sort(typeList, GlobTypeComparator.INSTANCE);
        for (GlobType type : typeList) {
            Map<GlobType, Field> hasCycle = new HashMap<GlobType, Field>();
            if (!processLinks(type, hasCycle, createSequence, done, postUpdate, globLinkModel)) {
                throwCycleException(hasCycle);
            }
        }
    }

    private static boolean processLinks(final GlobType objectType,
                                        final Map<GlobType, Field> hasCycle,
                                        final List<GlobType> order,
                                        final Set<GlobType> done,
                                        final Set<GlobType> postUpdate, DefaultMutableGlobLinkModel globLinkModel) {
        if (done.contains(objectType)) {
            return true;
        }
        if (hasCycle.containsKey(objectType)) {
            return false;
        }

        Link[] links = globLinkModel.getLinks(objectType);
        for (Link link : links) {
            link.apply((sourceField, targetField) -> {
                hasCycle.remove(objectType);
                hasCycle.put(objectType, sourceField);
                if (!processLinks(targetField.getGlobType(), hasCycle, order, done, postUpdate, globLinkModel)) {
                    if (sourceField.isRequired()) {
                        hasCycle.remove(objectType);
                        throwCycleException(hasCycle);
                    }
                    else {
                        postUpdate.add(objectType);
                    }
                }
            });
        }

        done.add(objectType);
        order.add(objectType);
        hasCycle.remove(objectType);
        return true;
    }

    private static void throwCycleException(Map<GlobType, Field> hasCycle) {
        throw new InvalidData(
            "Cycles found with required fields:" + Strings.LINE_SEPARATOR + Strings.toString(hasCycle));
    }
}
