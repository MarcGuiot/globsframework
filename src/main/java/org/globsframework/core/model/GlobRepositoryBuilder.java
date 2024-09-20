package org.globsframework.core.model;

import org.globsframework.core.model.repository.DefaultGlobIdGenerator;
import org.globsframework.core.model.repository.DefaultGlobRepository;
import org.globsframework.core.model.repository.GlobIdGenerator;
import org.globsframework.core.model.repository.StrictGlobRepository;
import org.globsframework.core.utils.exceptions.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GlobRepositoryBuilder {
    private List<Glob> globList = new ArrayList<>();
    private final GlobIdGenerator idGenerator;
    private ExceptionHandler exceptionHanlder;

    public static GlobRepository createEmpty() {
        return init().get();
    }

    public static GlobRepositoryBuilder init() {
        return new GlobRepositoryBuilder(new DefaultGlobIdGenerator(), null);
    }

    public static GlobRepositoryBuilder init(GlobIdGenerator idGenerator) {
        return new GlobRepositoryBuilder(idGenerator, null);
    }

    public static GlobRepositoryBuilder init(GlobIdGenerator idGenerator, ExceptionHandler exceptionHanlder) {
        return new GlobRepositoryBuilder(idGenerator, exceptionHanlder);
    }

    private GlobRepositoryBuilder(GlobIdGenerator idGenerator, ExceptionHandler exceptionHanlder) {
        this.idGenerator = idGenerator;
        this.exceptionHanlder = exceptionHanlder;
    }

    public GlobRepositoryBuilder add(Glob... globs) {
        globList.addAll(Arrays.asList(globs));
        return this;
    }

    public GlobRepositoryBuilder add(Collection<Glob> globs) {
        globList.addAll(globs);
        return this;
    }

    public GlobRepository get() {
        DefaultGlobRepository repository = new DefaultGlobRepository(idGenerator);
        repository.add(globList);
        if (exceptionHanlder != null) {
            return new StrictGlobRepository(repository, exceptionHanlder);
        }
        return repository;
    }
}
