package org.globsframework.metamodel;

import org.globsframework.metamodel.links.Link;

public interface GlobLinkModel {

   Link[] getLinks(GlobType sourceType);

   Link getLink(GlobType type, String fieldName);

   Link[] getInboundLinks(GlobType targetType);
}
