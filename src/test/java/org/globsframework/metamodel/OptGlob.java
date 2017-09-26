package org.globsframework.metamodel;

import org.globsframework.metamodel.fields.LongField;
import org.globsframework.model.MutableGlob;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Date;

public class OptGlob extends AbstractOptGlob {
  static GlobType type;
  String v1;
  String v2;
  int a;
  double b;
  long date;

  public GlobType getType() {
    return type;
  }

  public Object getValue(Field field) throws ItemNotFound {
    switch (field.getIndex()) {
      case 0:
        return v1;
      case 1:
        return v2;
      case 2:
        return a / 100.;
      case 3:
        return b;
      case 4:
        return new Date(date);
    }
    return null;
  }

   public MutableGlob set(LongField field, Long value) {
      return setObject(field, value);
   }

   public MutableGlob setObject(Field field, Object value) {
    if (value != null) {
      switch (field.getIndex()) {
        case 0:
          v1 = (String)value;
          break;
        case 1:
          v2 = (String)value;
          break;
        case 2:
          a = (int)(((Double)value).doubleValue() * 100.);
          break;
        case 3:
          b = ((Double)value).doubleValue();
          break;
        case 4:
          date = ((Date)value).getTime();
          break;
        default:
          throw new RuntimeException(field + " not found in " + type);
      }
    }
    else {
      setNull(field);
    }
    return null;
  }

  private void setNull(Field field) {
      switch (field.getIndex()) {
        case 0:
          v1 = null;
          return;
        case 1:
          v2 = null;
          return ;
        case 2:
          a = 0;
          return ;
        case 3:
          b = Double.NaN;
          return;
        case 4:
          date = 0;
        default:
          throw new RuntimeException(field + " not found in " + type);
      }
    }
}
