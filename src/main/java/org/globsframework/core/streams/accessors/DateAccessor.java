package org.globsframework.core.streams.accessors;

import java.time.LocalDate;

public interface DateAccessor extends Accessor {

    LocalDate getDate();

    boolean wasNull();
}
