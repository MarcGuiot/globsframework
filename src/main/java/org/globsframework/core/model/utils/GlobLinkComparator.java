package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.links.Link;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;

import java.util.Comparator;

public class GlobLinkComparator implements Comparator<Glob> {
    private Link link;
    private GlobRepository repository;
    private Comparator<Glob> targetComparator;

    public GlobLinkComparator(Link link, GlobRepository repository, Comparator<Glob> targetComparator) {
        this.link = link;
        this.repository = repository;
        this.targetComparator = targetComparator;
    }

    public int compare(Glob glob1, Glob glob2) {
        Glob target1 = repository.findLinkTarget(glob1, link);
        Glob target2 = repository.findLinkTarget(glob2, link);
        return targetComparator.compare(target1, target2);
    }
}
