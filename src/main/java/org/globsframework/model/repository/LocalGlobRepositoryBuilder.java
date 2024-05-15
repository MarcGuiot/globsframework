package org.globsframework.model.repository;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalGlobRepositoryBuilder {
    private GlobRepository reference;
    private DefaultGlobRepository temporary;
    private List<GlobType> globTypes = new ArrayList<GlobType>();
    private List<Glob> globs = new ArrayList<>();

    public static LocalGlobRepositoryBuilder init(GlobRepository reference) {
        return new LocalGlobRepositoryBuilder(reference);
    }

    private LocalGlobRepositoryBuilder(GlobRepository reference) {
        this.reference = reference;
        this.temporary = new DefaultGlobRepository(reference.getIdGenerator());
    }

    public LocalGlobRepositoryBuilder copy(GlobType... types) {
        globTypes.addAll(Arrays.asList(types));
        for (GlobType type : types) {
            for (Glob glob : reference.getAll(type)) {
                doCopy(glob);
            }
        }
        return this;
    }

    public LocalGlobRepositoryBuilder copy(List<Glob> list) {
        globs.addAll(list);
        for (Glob glob : list) {
            doCopy(glob);
        }
        return this;
    }

    public LocalGlobRepositoryBuilder copy(Glob... globs) {
        this.globs.addAll(Arrays.asList(globs));
        for (Glob glob : globs) {
            doCopy(glob);
        }
        return this;
    }

    private void doCopy(Glob glob) {
        temporary.add(glob.duplicate());
    }

    public LocalGlobRepository get() {
        return new LocalGlobRepository(reference, temporary, globTypes, globs);
    }
}
