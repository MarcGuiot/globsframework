package org.globsframework.core.streams.accessors;

import java.time.ZonedDateTime;

public interface DateTimeAccessor extends Accessor {

    ZonedDateTime getDateTime();

    boolean wasNull();
}
