package org.globsframework.core.metamodel.utils;

import org.globsframework.core.metamodel.GlobLinkModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.links.Link;

public class EmptyGlobLinkModel implements GlobLinkModel {
    public static final GlobLinkModel EMPTY = new EmptyGlobLinkModel();
    public static final Link[] LINKS = new Link[0];

    public Link[] getLinks(GlobType globType) {
        return LINKS;
    }

    public Link[] getInboundLinks(GlobType type) {
        return LINKS;
    }

    public Link getLink(GlobType type, String fieldName) {
        return null;
    }
}
