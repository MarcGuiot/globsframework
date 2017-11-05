package org.globsframework.model;

import org.globsframework.model.repository.*;
import org.globsframework.utils.exceptions.ExceptionHandler;

public class GlobRepositoryBuilder {
    private GlobList globList = new GlobList();
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
        globList.addAll(globs);
        return this;
    }

    public GlobRepositoryBuilder add(GlobList globs) {
        globList.addAll(globs);
        return this;
    }

    public GlobRepository get() {
        DefaultGlobRepository repository = new DefaultGlobRepository(idGenerator);
        repository.add(globList);
        if (idGenerator instanceof DefaultCheckedGlobIdGenerator) {
            ((DefaultCheckedGlobIdGenerator)idGenerator).setRepository(repository);
        }
        if (exceptionHanlder != null) {
            return new StrictGlobRepository(repository, exceptionHanlder);
        }
        return repository;
    }
}
