package org.globsframework.utils.serialization;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SerializedInputOutputFactory {
  private static boolean checked = false;

  static public SerializedOutput init(OutputStream outputStream) {
    DefaultSerializationOutput serializationOutput = new DefaultSerializationOutput(outputStream);
    if (checked) {
      return new SerializedOutputChecker(serializationOutput);
    }
    return serializationOutput;
  }

  static public SerializedInput init(InputStream inputStream) {
    DefaultSerializationInput serializationInput = new DefaultSerializationInput(inputStream);
    if (checked) {
      return new SerializationInputChecker(serializationInput);
    }
    return serializationInput;
  }

  public static SerializedInput init(byte[] bytes) {
    return init(new ByteArrayInputStream(bytes));
  }
}
