package org.globsframework.utils;

/**
 * Note: beware of the serialization of objects that use this value - they must be fixed to refer to this specific
 * instance on deserialization
 */
public class Unset {
    public static final Object VALUE = new Object() {
        public String toString() {
            return "<unset>";
        }
    };
}
