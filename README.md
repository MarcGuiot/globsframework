# globsFramework
This is a metamodel framework.

The goal of this framework is to replace beans by an object called a Glob. This Glob is a kind of map. The key to access a field 
is not a string but an instance of a Field. Theses Fields (for a given 'bean') are encapsulated in an instance of GlobType. GlobType and Field 
contains additional information called annotation (like java annotation but there a Glob, of course ;-) ).

Thanks to that, we can write generic code (not magic;-) that serialize in XML/Json/binary,
that insert in a DB, and many more.

Most if not all the code that use introspection to achieve there work can be replaced by using the GlobType. As a GlobType 
expose a way to access field, and where the field have a known fix type, the written code is fully control.
Actually, a GlobType is a model for data and only data, in java we use classes to encapsulate data and many more, it is easy to understand that
any code that depends on data like serialisation, db access, etc is really easier to write as we don't have the complexity 
of the many way to access and use a class compared to a Glob.

So, GlobsFramework is useful everywhere input/output is used, but also inside our own code :
Here are some usage example :
* Any code that must control flow of data, of the configuration, etc.
* Any code that is configurable using user input.
* If you want generic way to filter, transform data.

It's main drawback is the fact that when you start to use it, it's 'power' is achieved only if all your code use Glob.
The advantage is that GlobsFramework is open source, very small, without dependency (only slf4j for logging) and so, is easy to maintain.

The framework (or a private version) is used by 3 company.

Let's take a look at it.

There is multiple way to create a GlobType.

## Here an example of a partial dynamically created GlobType 
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

## static way
```
public static class AType {
   public static GlobType TYPE;
   
   public static StringField string;
   
   public static IntegerField int;
   
   public static LongField long;
   
   static {
      GlobTypeLoaderFactory.init(AType.class).load();
   }
}

Glob data = AType.instance().

```