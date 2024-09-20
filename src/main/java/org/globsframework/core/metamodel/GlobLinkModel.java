package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.links.Link;

public interface GlobLinkModel {

    Link[] getLinks(GlobType sourceType);

    Link getLink(GlobType type, String fieldName);

    Link[] getInboundLinks(GlobType targetType);
}
