package org.globsframework.core.model.utils;

import org.globsframework.core.model.ChangeSetVisitor;

public interface ChangeVisitor extends ChangeSetVisitor {
    void complete();
}
