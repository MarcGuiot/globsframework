package org.globsframework.model;

public interface ExtensionService {

   <T> T getExtension(Class<T> extensionType);

}
