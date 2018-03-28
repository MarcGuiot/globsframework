package org.globsframework.model;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.impl.DefaultGlobFactoryService;

public interface GlobFactoryService {

    GlobFactory get(GlobType type);

    class Builder {
        static private GlobFactoryService builderFactory =
                new DispatchGlobFactoryService();

        public static GlobFactoryService getBuilderFactory() {
            return builderFactory;
        }
    }

    class DispatchGlobFactoryService implements GlobFactoryService {
        final GlobFactoryService globFactoryService = new DefaultGlobFactoryService();
        final GlobFactoryService specialized;

        public DispatchGlobFactoryService() {
            String className = System.getProperty("org.globsframework.builder", DefaultGlobFactoryService.class.getName());
            try {
                specialized = (GlobFactoryService) Class.forName(className).newInstance();
            } catch (Exception e) {
                throw new RuntimeException("fail to load" + className, e);
            }
        }

        public GlobFactory get(GlobType type) {
            GlobFactory globFactory = specialized.get(type);
            return globFactory != null ? globFactory : globFactoryService.get(type);
        }
    }
}
