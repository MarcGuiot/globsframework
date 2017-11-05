package org.globsframework.model.format;

import org.globsframework.metamodel.links.Link;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.utils.AbstractGlobStringifier;

public class GlobLinkStringifier extends AbstractGlobStringifier {
    private GlobStringifier targetStringifier;
    private Link link;

    public GlobLinkStringifier(Link link, GlobStringifier targetStringifier) {
        this.link = link;
        this.targetStringifier = targetStringifier;
    }

    public String toString(Glob glob, GlobRepository repository) {
        return targetStringifier.toString(repository.findLinkTarget(glob, link), repository);
    }
}
