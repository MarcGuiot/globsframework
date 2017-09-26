package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.annotations.Required;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;

public class DummyObjectWithRequiredFields {
  public static GlobType TYPE;

  @KeyField
  public static IntegerField ID;

  @Required
  public static IntegerField VALUE;

  @Required
  public static StringField NAME;

  static {
     GlobTypeLoaderFactory.create(DummyObjectWithRequiredFields.class).load();
  }
}
