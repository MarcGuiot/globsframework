package org.globsframework.core.metamodel;

import org.globsframework.core.utils.exceptions.ItemNotFound;
import org.globsframework.core.utils.exceptions.TypeNotFound;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface GlobTypeResolver {
    GlobType findType(String name);

    default Optional<GlobType> find(String name) {
        return Optional.ofNullable(findType(name));
    }

    default GlobType getType(String name) throws ItemNotFound {
        GlobType globType = findType(name);
        if (globType == null) {
            throw new TypeNotFound(name);
        }
        return globType;
    }

    static GlobTypeResolver chain(GlobTypeResolver... resolvers) {
        return name -> {
            for (GlobTypeResolver resolver : resolvers) {
                GlobType globType = resolver.findType(name);
                if (globType != null) {
                    return globType;
                }
            }
            return null;
        };
    }

    GlobTypeResolver ERROR = name -> {
        throw new TypeNotFound(name);
    };

    GlobTypeResolver NULL = name -> null;

    static GlobTypeResolver from(GlobType type) {
        return name -> type.getName().equals(name) ? type : null;
    }

    static GlobTypeResolver from(GlobType... types) {
        Map<String, GlobType> collect = Arrays.stream(types).collect(Collectors.toMap(GlobType::getName, Function.identity()));
        return collect::get;
    }

    static GlobTypeResolver from(Collection<GlobType> types) {
        Map<String, GlobType> collect = types.stream().collect(Collectors.toMap(GlobType::getName, Function.identity()));
        return collect::get;
    }

}
