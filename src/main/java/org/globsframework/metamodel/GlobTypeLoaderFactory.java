package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.InitUniqueGlob;
import org.globsframework.metamodel.annotations.InitUniqueKey;
import org.globsframework.metamodel.impl.FieldInitializeProcessorServiceImpl;
import org.globsframework.metamodel.impl.GlobTypeLoaderImpl;
import org.globsframework.metamodel.links.DirectLink;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.links.impl.UnInitializedLink;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;

import java.lang.annotation.Annotation;

public class GlobTypeLoaderFactory {
    static Object LOCK = new Object();
    static FieldInitializeProcessorService fieldInitializeProcessorService;

    public static void createAndLoad(Class<?> targetClass) {
        build(targetClass).create().load();
    }

    public static GlobTypeLoader create(Class<?> targetClass) {
        return create(targetClass, (String[]) null, null);
    }

    public static FactoryBuilder build(Class<?> targetClass) {
        return new DefaultFactoryBuilder(targetClass);
    }

    public static GlobTypeLoader create(Class<?> targetClass, String name) {
        return create(targetClass, (String[]) null, name);
    }

    public static GlobTypeLoader create(Class<?> targetClass, String modelName, String name) {
        return create(targetClass, modelName == null ? null : modelName.split("\\."), name);
    }

    public static GlobTypeLoader create(Class<?> targetClass, String[] modelName, String name) {
        initProcessorService();
        FieldInitializeProcessorService service = fieldInitializeProcessorService;
        return create(targetClass, modelName, name, service);
    }

    public static GlobTypeLoader create(Class<?> targetClass, String[] modelName, String name,
                                        FieldInitializeProcessorService service) {
        return new GlobTypeLoaderImpl(targetClass, modelName, name, service);
    }

    static public FieldInitializeProcessorService getFieldInitializeProcessorService() {
        if (fieldInitializeProcessorService == null) {
            FieldInitializeProcessorServiceImpl service = new FieldInitializeProcessorServiceImpl();
            addFieldInitializeProcessorService(service);
            fieldInitializeProcessorService = service;
        }
        return fieldInitializeProcessorService;
    }

    private static void initProcessorService() {
        if (fieldInitializeProcessorService == null) {
            FieldInitializeProcessorServiceImpl newProcessorService = null;
            synchronized (LOCK) {
                if (fieldInitializeProcessorService == null) {
                    newProcessorService = new FieldInitializeProcessorServiceImpl();
                    addFieldInitializeProcessorService(newProcessorService);
                }
            }
            if (fieldInitializeProcessorService == null && newProcessorService != null) {
                fieldInitializeProcessorService = newProcessorService;
            }
        }
    }

    // this code is expected to be called very soon during the initialisation
    // before ony GlobType is created from the GlobTypeLoader
    // warning : a glob type created from a globTypeBuilder will trigger
    // a call to GlobTypeLoader to access globs that modelize annotations

    private static void addFieldInitializeProcessorService(FieldInitializeProcessorServiceImpl newProcessorService) {
        newProcessorService.add(Link.class, UnInitializedLink::new);
        newProcessorService.add(DirectLink.class, UnInitializedLink::new);
        newProcessorService.add(Key.class, (type, annotations, nativeAnnotations) -> {
            for (Annotation annotation : nativeAnnotations) {
                if (annotation.annotationType().equals(InitUniqueKey.class)) {
                    return KeyBuilder.newEmptyKey(type);
                }
            }
            return null;
        });
        newProcessorService.add(Glob.class, (type, annotations, nativeAnnotations) -> {
            for (Annotation annotation : nativeAnnotations) {
                if (annotation.annotationType().equals(InitUniqueGlob.class)) {
                    return type.instantiate();
                }
            }
            return null;
        });
//      newProcessorService.add(Glob.class, (type, annotations, nativeAnnotations) -> {
//         for (Annotation annotation : nativeAnnotations) {
//            if (annotation.annotationType().equals(KeyIndex.class)) {
//               return type.instantiate().set(KeyAnnotationType.INDEX, ((KeyIndex)annotation).value());
//            }
//         }
//         return null;
//      });
    }

    public interface FactoryBuilder {
        FactoryBuilder withName(String name);

        FactoryBuilder withModel(String name);

        GlobTypeLoader create();
    }

    static class DefaultFactoryBuilder implements FactoryBuilder {
        private final Class<?> klass;
        private String name;
        private String[] model;

        DefaultFactoryBuilder(Class<?> klass) {
            this.klass = klass;
        }

        public FactoryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public FactoryBuilder withModel(String model) {
            this.model = model.split("\\.");
            return this;
        }

        public GlobTypeLoader create() {
            return GlobTypeLoaderFactory.create(klass, model, name);
        }
    }

}
