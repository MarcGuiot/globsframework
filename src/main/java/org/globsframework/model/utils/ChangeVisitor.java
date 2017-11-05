package org.globsframework.model.utils;

import org.globsframework.model.ChangeSetVisitor;

public interface ChangeVisitor extends ChangeSetVisitor {
    void complete();
}
