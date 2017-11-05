package org.globsframework.model.format;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.links.Link;

public interface DescriptionService {

    Formats getFormats();

    String getLabel(GlobType type);

    String getLabel(Field field);

    String getLabel(Link link);

    GlobStringifier getStringifier(GlobType globType);

    GlobListStringifier getListStringifier(GlobType globType);

    GlobStringifier getStringifier(Field field);

    GlobListStringifier getListStringifier(Field field);

    GlobStringifier getStringifier(Link link);

    GlobListStringifier getListStringifier(Link link);

    GlobListStringifier getListStringifier(Link link, String textForEmptySelection, String textForMultipleValues);
}
