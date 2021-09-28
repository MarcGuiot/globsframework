# globsframework
This is a metamodel framework.

The goal of this framwork is to replace beans by an object called a Glob. This Glob is a kind of map. The key to access a field 
is not a string but an instance of a Field. Theses Fields (for a given 'bean') are encapsulated in an instance of GlobType. GlobType and Field 
contains additional information anyone can add.

Thanks to that, we can write generic code (not magic;-) that serialize in XML/Json/binary,
that insert in a DB. It helps also to map object to other object using some configuration files.
There is multiple way to create a GlobType.

## Here an example :
In these examples, we create a GlobType, associate an 'annotation' called NamingField to a field, set and get a value for the given field,
and retreive the field using the NamingFieldAnnotationType which is also a Glob.

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
