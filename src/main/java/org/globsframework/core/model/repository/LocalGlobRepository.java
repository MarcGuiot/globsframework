package org.globsframework.core.model.repository;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.ChangeSetListener;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.model.utils.ChangeSetAggregator;
import org.globsframework.core.utils.exceptions.InvalidState;

import java.util.*;
import java.util.stream.Collectors;

public class LocalGlobRepository extends GlobRepositoryDecorator {

    private GlobRepository reference;
    private List<GlobType> globTypes;
    private Collection<Glob> globs;
    private ChangeSetAggregator aggregator;

    LocalGlobRepository(GlobRepository reference, GlobRepository temporary,
                        List<GlobType> globTypes, Collection<Glob> globs) {
        super(temporary);
        this.reference = reference;
        this.globTypes = globTypes;
        this.globs = globs;
        this.aggregator = new ChangeSetAggregator(temporary);
    }

    public boolean containsChanges() {
        return !getCurrentChanges().isEmpty();
    }

    public ChangeSet getCurrentChanges() {
        return aggregator.getCurrentChanges();
    }

    public void commitChanges(boolean dispose) {
        try {
            reference.apply(aggregator.getCurrentChanges());
        } finally {
            aggregator.reset();
            if (dispose) {
                dispose();
            }
        }
    }

    public void rollback() {
        aggregator.dispose();
        ArrayList<Glob> list = new ArrayList<>(reference.getAll(globTypes.toArray(new GlobType[globTypes.size()])));
        list.addAll(globs);
        Set<GlobType> globTypes = new HashSet<GlobType>(this.globTypes);
        globTypes.addAll(list.stream().map(Glob::getType).collect(Collectors.toSet()));
        globTypes.addAll(getRepository().getTypes());
        getRepository().reset(list, globTypes.toArray(new GlobType[0]));
        aggregator = new ChangeSetAggregator(getRepository());
    }

    public void dispose() {
        setRepository(null);
        aggregator.dispose();
    }

    protected GlobRepository getRepository() {
        GlobRepository repository = super.getRepository();
        if (repository == null) {
            throw new InvalidState("repository is disabled");
        }
        return repository;
    }

    public void removeTrigger(ChangeSetListener listener) {
        if (super.getRepository() != null) {
            super.removeTrigger(listener);
        }
    }

    public void removeChangeListener(ChangeSetListener listener) {
        if (super.getRepository() != null) {
            super.removeChangeListener(listener);
        }
    }
}
