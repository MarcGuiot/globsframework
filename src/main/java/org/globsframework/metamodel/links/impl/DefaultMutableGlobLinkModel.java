package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.*;
import org.globsframework.metamodel.annotations.FieldNameAnnotationType;
import org.globsframework.metamodel.annotations.LinkModelNameAnnotationType;
import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.impl.DefaultAnnotations;
import org.globsframework.metamodel.links.DirectLink;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.collections.MapOfMaps;
import org.globsframework.utils.exceptions.GlobsException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DefaultMutableGlobLinkModel implements MutableGlobLinkModel {
    public static final Link[] EMPTY = new Link[0];
    private final Map<GlobType, Link[]> links = new HashMap<>();
    private final Map<GlobType, Link[]> inboundLinks = new HashMap<>();
    private final MapOfMaps<GlobType, String, Link> outputLinkByName = new MapOfMaps<>();

    public DefaultMutableGlobLinkModel(GlobModel model) {
        for (GlobType type : model) {
            LinkRegister registered = type.getRegistered(LinkRegister.class);
            if (registered != null) {
                registered.register(this);
            }
        }
    }

    public Link[] getLinks(GlobType globType) {
        Link[] retLinks = links.get(globType);
        return retLinks == null ? EMPTY : retLinks;
    }

    public Link[] getInboundLinks(GlobType type) {
        Link[] retLinks = inboundLinks.get(type);
        return retLinks == null ? EMPTY : retLinks;
    }

    public Link getLink(GlobType type, String fieldName) {
        return outputLinkByName.get(type, fieldName);
    }

    public LinkBuilder getLinkBuilder(String modelName, String name, Glob... globAnnotations) {
        return getDirectLinkBuilder(modelName, name, globAnnotations);
    }

    public LinkBuilder getLinkBuilder(Annotations annotations) {
        if (annotations instanceof Link && !(annotations instanceof UnInitializedLink)) {
            return new AlreadyInitializedBuilder((Link)annotations, (link) -> {
                appendInSource(link);
                appendInTarget(link);
            });
        }
        Glob linkModel = annotations.findAnnotation(LinkModelNameAnnotationType.UNIQUE_KEY);
        Glob annotation = annotations.findAnnotation(FieldNameAnnotationType.UNIQUE_KEY);
        DirectLinkBuilder builder = getDirectLinkBuilder(linkModel != null ? linkModel.get(LinkModelNameAnnotationType.NAME) : null,
                                                         annotation != null ? annotation.get(FieldNameAnnotationType.NAME) : null);
        builder.addAnnotations(annotations.streamAnnotations());
        return builder;
    }

    interface OnPublish {
        void publish(Link link);
    }

    public DirectLinkBuilder getDirectLinkBuilder(String modelName, String name, Glob... globAnnotations) {
        return new DefaultDirectLinkBuilder(modelName, name,
                                            (link) -> {
                                                appendInSource(link);
                                                appendInTarget(link);
                                            }, new DefaultAnnotations(globAnnotations));
    }

    public DirectLinkBuilder getDirectLinkBuilder(Annotations annotations) {
        OnPublish publish = (createdLink) -> {
            appendInSource(createdLink);
            appendInTarget(createdLink);
        };
        if (annotations instanceof Link && !(annotations instanceof UnInitializedLink)) {
            return new AlreadyInitializedBuilder((Link)annotations, publish);
        }
        Glob fieldName = annotations.getAnnotation(FieldNameAnnotationType.UNIQUE_KEY);
        Glob modelNameAnnotation = annotations.findAnnotation(LinkModelNameAnnotationType.UNIQUE_KEY);
        String modelName = null;
        if (modelNameAnnotation != null) {
            modelName = modelNameAnnotation.get(LinkModelNameAnnotationType.NAME);
        }
        return new DefaultDirectLinkBuilder(modelName, fieldName.get(FieldNameAnnotationType.NAME),
                                            publish, annotations);
    }

    private void appendInSource(Link link) {
        Link[] current = links.get(link.getSourceType());
        outputLinkByName.put(link.getSourceType(), link.getName(), link);
        current = current == null ? new Link[1] : Arrays.copyOf(current, current.length + 1);
        current[current.length - 1] = link;
        links.put(link.getSourceType(), current);
    }

    private void appendInTarget(Link link) {
        Link[] current = inboundLinks.get(link.getTargetType());
        current = current == null ? new Link[1] : Arrays.copyOf(current, current.length + 1);
        current[current.length - 1] = link;
        inboundLinks.put(link.getTargetType(), current);
    }

    private static class AlreadyInitializedBuilder implements DirectLinkBuilder {
        private Link link;
        private OnPublish publish;

        public AlreadyInitializedBuilder(Link link, OnPublish publish) {
            this.link = link;
            this.publish = publish;
        }

        public DirectLinkBuilder addAnnotation(Glob annotation) {
            if (link.findAnnotation(annotation.getKey()) == null) {
                throw new GlobsException(link + " already initialized but doesn't contains " + annotation.getKey());
            }
            return this;
        }

        public DirectLinkBuilder add(Field id1, Field id2) {
            if (!link.apply(new FieldMappingFunction() {
                boolean found = false;

                public void process(Field sourceField, Field targetField) {
                    if (id1 == sourceField && targetField == id2) {
                        found = true;
                    }
                }
            }).found) {
                throw new GlobsException(link + " already initialized but doesn't contains " + id1 + "=>" + id2);
            }

            return this;
        }

        public DirectLink publish() {
            publish.publish(link);
            return (DirectLink)link;
        }

        public DirectLinkBuilder addAnnotations(Stream<Glob> globs) {
            globs.forEach(this::addAnnotation);
            return this;
        }

        public Stream<Glob> streamAnnotations() {
            return link.streamAnnotations();
        }

        public Stream<Glob> streamAnnotations(GlobType type) {
            return link.streamAnnotations(type);
        }

        public boolean hasAnnotation(Key key) {
            return link.hasAnnotation(key);
        }

        public Glob getAnnotation(Key key) {
            return link.getAnnotation(key);
        }

        public Glob findAnnotation(Key key) {
            return link.findAnnotation(key);
        }

    }
}
