package org.globsframework.core.model;

public interface ExtensionService {

    <T> T getExtension(Class<T> extensionType);

}
