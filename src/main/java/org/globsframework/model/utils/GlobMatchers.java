package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.Key;
import org.globsframework.utils.Strings;
import org.globsframework.utils.Utils;

import java.util.*;

public class GlobMatchers {

   public static GlobMatcher ALL = new GlobMatcher() {
      public boolean matches(Glob item, GlobRepository repository) {
         return true;
      }

      public String toString() {
         return "all";
      }
   };

   public static GlobMatcher NONE = new GlobMatcher() {
      public boolean matches(Glob item, GlobRepository repository) {
         return false;
      }

      public String toString() {
         return "none";
      }
   };

   public static GlobMatcher fieldEquals(IntegerField field, Integer value) {
      return fieldEqualsObject(field, value);
   }

   public static GlobMatcher fieldIn(final IntegerField field, final Integer... values) {
      if (values.length == 0) {
         return GlobMatchers.NONE;
      }
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            Integer fieldValue = item.get(field);
            for (Integer value : values) {
               if (Utils.equal(fieldValue, value)) {
                  return true;
               }
            }
            return false;
         }

         public String toString() {
            return field.getFullName() + " in " + Arrays.toString(values);
         }
      };
   }

   public static GlobMatcher fieldEquals(StringField field, String value) {
      return fieldEqualsObject(field, value);
   }

   public static GlobMatcher fieldEqualsIgnoreCase(final StringField field, final String value) {
      return new GlobMatcher() {
         public boolean matches(Glob glob, GlobRepository repository) {
            return Utils.equalIgnoreCase(glob.get(field), value);
         }

         public String toString() {
            return field.getFullName() + " equals [ignoreCase] " + value;
         }
      };
   }

   public static GlobMatcher fieldEquals(DoubleField field, Double value) {
      return fieldEqualsObject(field, value);
   }

   public static GlobMatcher fieldEquals(BlobField field, byte[] value) {
      return fieldEqualsObject(field, value);
   }

   public static GlobMatcher isTrue(BooleanField field) {
      return fieldEqualsObject(field, true);
   }

   public static GlobMatcher isNotTrue(final BooleanField field) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            Boolean value = item.get(field);
            return value == null || value == false;
         }

         public String toString() {
            return field.getName() + " is not true";
         }
      };
   }

   public static GlobMatcher isFalse(BooleanField field) {
      return fieldEqualsObject(field, false);
   }

   public static GlobMatcher fieldEquals(BooleanField field, Boolean value) {
      return fieldEqualsObject(field, value);
   }

   public static GlobMatcher fieldEquals(LongField field, Long value) {
      return fieldEqualsObject(field, value);
   }

   public static GlobMatcher fieldEqualsObject(Field field, Object value) {
      return new SingleFieldMatcher(field, value);
   }

   public static GlobMatcher fieldContainsIgnoreCaseAndAccents(final StringField field, final String value) {
      if (Strings.isNullOrEmpty(value)) {
         return ALL;
      }
      final String rawValue = normalize(value);
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            String actual = item.get(field);
            return actual != null && normalize(actual).contains(rawValue);
         }

         public String toString() {
            return "field " + field.getFullName() + " contains[ignoreCase+Accents] " + value;
         }
      };
   }

   private static String normalize(String value) {
      return Strings.unaccent(value.toLowerCase());
   }

   public static GlobMatcher contained(Field field, Object... values) {
      return contained(field, Arrays.asList(values));
   }

   public static GlobMatcher contained(Field field, Collection values) {
      if ((values == null) || values.isEmpty()) {
         return NONE;
      }
      if (values.size() == 1) {
         return new SingleFieldMatcher(field, values.iterator().next());
      }
      return new CollectionFieldMatcher(field, values);
   }

   /**
    * Accepts all Globs who are linked to a given Glob.
    */

   public static GlobMatcher linkedTo(Glob target, final Link link) {
      if (target == null) {
         return NONE;
      }
      return link.apply(new FieldMappingFunction() {
         GlobMatcher matcher = ALL;

         public void process(Field sourceField, Field targetField) {
            matcher = and(matcher, fieldEqualsObject(sourceField, target.getValue(targetField)));
         }
      }).matcher;
   }

   public static GlobMatcher linkTargetFieldEquals(final Link link, final Field targetField,
                                                   final Object targetFieldValue) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            final Glob target = repository.findLinkTarget(item, link);
            if (target == null) {
               return false;
            }
            Object value = target.getValue(targetField);
            return value != null ? value.equals(targetFieldValue) : targetFieldValue == null;
         }

         public String toString() {
            return link + " is linked to glob with " + targetField.getFullName() + " = " + targetFieldValue;
         }
      };
   }

   public static GlobMatcher linkedTo(final Link link, final GlobMatcher linkedObjectMatcher) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            Glob target = repository.findLinkTarget(item, link);
            return (target != null) && linkedObjectMatcher.matches(target, repository);
         }

         public String toString() {
            return link + " is linked to matcher " + linkedObjectMatcher;
         }
      };
   }


   public static GlobMatcher isNull(final Field field) {
      return new GlobMatcher() {
         public boolean matches(Glob glob, GlobRepository repository) {
            return glob.getValue(field) == null;
         }

         public String toString() {
            return field.getFullName() + " is null";
         }
      };
   }

   public static GlobMatcher isNotNull(final Field field) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return item.getValue(field) != null;
         }

         public String toString() {
            return field.getFullName() + " is not null";
         }
      };
   }

   public static GlobMatcher isNullOrEmpty(final StringField field) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return Strings.isNullOrEmpty(item.get(field));
         }

         public String toString() {
            return field.getFullName() + " is null or empty";
         }
      };
   }

   public static GlobMatcher isNotEmpty(final StringField field) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return Strings.isNotEmpty(item.get(field));
         }

         public String toString() {
            return field.getFullName() + " is not empty";
         }
      };
   }

   public static GlobMatcher and(List<GlobMatcher> matchers) {
      GlobMatcher[] array = matchers.toArray(new GlobMatcher[matchers.size()]);
      return and(array);
   }

   public static GlobMatcher and(final GlobMatcher... matchers) {
      for (GlobMatcher matcher : matchers) {
         if (NONE.equals(matcher)) {
            return NONE;
         }
      }
      List<GlobMatcher> significantMatchers = new ArrayList<GlobMatcher>();
      for (GlobMatcher matcher : matchers) {
         if ((matcher != null) && (matcher != ALL)) {
            significantMatchers.add(matcher);
         }
      }
      if (significantMatchers.isEmpty()) {
         return ALL;
      }
      if (significantMatchers.size() == 1) {
         return significantMatchers.get(0);
      }
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            for (GlobMatcher matcher : matchers) {
               if (matcher != null && !matcher.matches(item, repository)) {
                  return false;
               }
            }
            return true;
         }

         public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("and {\n");
            for (GlobMatcher matcher : matchers) {
               builder.append(matcher).append('\n');
            }
            builder.append("}");
            return builder.toString();
         }
      };
   }

   public static GlobMatcher or(final GlobMatcher... matchers) {
      List<GlobMatcher> significantMatchers = new ArrayList<GlobMatcher>();
      for (GlobMatcher matcher : matchers) {
         if ((matcher != null) && matcher.equals(ALL)) {
            return ALL;
         }
         if ((matcher != null) && (matcher != NONE)) {
            significantMatchers.add(matcher);
         }
      }
      if (significantMatchers.isEmpty()) {
         return ALL;
      }
      if (significantMatchers.size() == 1) {
         return significantMatchers.get(0);
      }
      return new GlobMatcher() {
         public boolean matches(Glob glob, GlobRepository repository) {
            for (GlobMatcher matcher : matchers) {
               if (matcher.matches(glob, repository)) {
                  return true;
               }
            }
            return false;
         }

         public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("or {\n");
            for (GlobMatcher matcher : matchers) {
               builder.append(matcher).append('\n');
            }
            builder.append("}");
            return builder.toString();
         }
      };
   }

   public static GlobMatcher not(final GlobMatcher matcher) {
      return new GlobMatcher() {
         public boolean matches(Glob glob, GlobRepository repository) {
            return !matcher.matches(glob, repository);
         }

         public String toString() {
            return "not(" + matcher + ")";
         }
      };
   }

   public static GlobMatcher keyEquals(final Key key) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return key.equals(item.getKey());
         }

         public String toString() {
            return "key equals " + key;
         }
      };
   }

   public static GlobMatcher keyIn(final Collection<Key> keys) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return keys.contains(item.getKey());
         }

         public String toString() {
            return "key in " + keys;
         }
      };
   }


   public static GlobMatcher fieldIn(final IntegerField field, final Collection<Integer> values) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            Integer value = item.get(field);
            return value != null && values.contains(value);
         }

         public String toString() {
            return field.getFullName() + " in " + values;
         }
      };
   }

   public static GlobMatcher fieldGreaterOrEqual(final IntegerField field, final int value) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return item.get(field) >= value;
         }

         public String toString() {
            return field.getFullName() + " >= " + value;
         }
      };
   }

   public static GlobMatcher fieldStrictlyGreaterThan(final IntegerField field, final int value) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return item.get(field) > value;
         }

         public String toString() {
            return field.getFullName() + " > " + value;
         }
      };
   }

   public static GlobMatcher fieldStrictlyLessThan(final IntegerField field, final int value) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return item.get(field) < value;
         }

         public String toString() {
            return field.getFullName() + " < " + value;
         }
      };
   }

   public static GlobMatcher fieldLessOrEqual(final IntegerField field, final int value) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return item.get(field) <= value;
         }

         public String toString() {
            return field.getFullName() + " <= " + value;
         }
      };
   }

   public static GlobMatcher fieldContained(final Field field, final Collection values) {
      return new GlobMatcher() {
         public boolean matches(Glob item, GlobRepository repository) {
            return values.contains(item.getValue(field));
         }

         public String toString() {
            return field.getFullName() + " in " + values;
         }
      };

   }


   private static class SingleFieldMatcher implements GlobMatcher {
      private Field field;
      private Object value;

      private SingleFieldMatcher(Field field, Object value) {
         this.field = field;
         this.value = value;
      }

      public boolean matches(Glob glob, GlobRepository repository) {
         return Utils.equal(value, glob.getValue(field));
      }

      public String toString() {
         return field.getFullName() + " == " + value;
      }
   }

   private static class CollectionFieldMatcher implements GlobMatcher {
      private Field field;
      private Collection values;

      private CollectionFieldMatcher(Field field, Collection values) {
         this.field = field;
         this.values = values;
      }

      public boolean matches(Glob glob, GlobRepository repository) {
         return values.contains(glob.getValue(field));
      }

      public String toString() {
         return field.getFullName() + " in " + values;
      }
   }

}
