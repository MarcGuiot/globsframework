package org.globsframework.model.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeBuilderFactory;
import org.globsframework.metamodel.annotations.KeyAnnotationType;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.metamodel.GlobTypeBuilder;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;
import org.junit.Assert;
import org.junit.Test;

public class CompositeKeyTest {

   @Test
   public void keyFieldNotAtBegin() throws Exception {
      GlobTypeBuilder builder = GlobTypeBuilderFactory.create("CompositeKey");
      builder.addStringField("field_1");
      IntegerField id1 = builder.declareIntegerField("id1", KeyAnnotationType.UNINITIALIZED);
      builder.addStringField("field_2");
      IntegerField id2 = builder.declareIntegerField("id2", KeyAnnotationType.UNINITIALIZED);
      IntegerField id3 = builder.declareIntegerField("id3", KeyAnnotationType.UNINITIALIZED);
      GlobType type = builder.get();
      Glob glob = type.instantiate().set(id1, 1).set(id2, 2).set(id3, 3);
      Key actual = KeyBuilder.init(type).set(id1, 1)
         .set(id2, 2)
         .set(id3, 3).get();
      Assert.assertEquals(glob.getKey(), actual);
      Assert.assertEquals(1, actual.get(id1).intValue());
      Assert.assertEquals(2, actual.get(id2).intValue());
      Assert.assertEquals(3, actual.get(id3).intValue());
   }
}