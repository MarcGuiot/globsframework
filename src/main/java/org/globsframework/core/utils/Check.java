package org.globsframework.core.utils;

import java.util.Objects;

public class Check {

    //from Objects with message
    public static <T> T requireNonNull(T obj, Object message) {
        if (obj == null)
            throw new NullPointerException(Objects.toString(message));
        return obj;
    }

}
