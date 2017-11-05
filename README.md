# globsframework
This is a metamodel framework.

The goal of this framwork is to replace beans by an object call a Glob. This Glob is a kind of map. The key to access a field 
is not a string but an instance of a Field. Theses Fields (for a given 'bean') are contains in an instance of a GlobType. GlobType and Field 
contains additional information onymone can add.

Thank to that we can write generic code (not magic;-) that serialize in XMl/Json/binary,
that insert in a DB. It help also to map object to other object using some configuration files.
There is multiple way to create a GlobType.

## Here an example :
In these example, we create a GlobType, associate an 'annotation' call NamingField to a field, set and get a value for the given field,
and retreive the field using the NamngFieldAnnotationType which is also a Glob.

```
         GlobType type = GlobTypeBuilderFactory.create("aType")
            .addIntegerKey("id")
            .addStringField("string", NamingFieldAnnotationType.UNIQUE_GLOB)
            .addIntegerField("int")
            .addLongField("long")
            .addDoubleField("double")
            .addBlobField("blob")
            .addBooleanField("boolean")
            .get();

        MutableGlob data = type.instantiate();

        StringField stringField = type.getTypedField("string");
        data.set(stringField, "Hello");

        assertEquals("Hello", data.get(stringField));

        Field namingField = data.getType().findFieldWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY);
        assertEquals("Hello", data.getValue(namingField));
```
