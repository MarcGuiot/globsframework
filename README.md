# globsFramework
This is a metamodel framework.

The goal of this framework is to replace beans by an object called a Glob. This Glob is a kind of map. The key to access a field 
is not a string but an instance of a Field. Theses Fields (for a given 'bean') are encapsulated in an instance of GlobType. GlobType and Field 
contains additional information called annotation (like java annotation but there a Glob, of course ;-) ).
 
Thanks to that, we can write generic code (not magic;-) that serialize in XML/Json/binary,
that insert in a DB, and many more.

Most, if not all, the code that use introspection to achieve there work can be replaced by using the GlobType. As a GlobType 
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

This framework do not hide complexity. For exemple, the jar for db access will help you create an sql request but will not generate
the join for you to follow a link (at least in this version). What I like here is that we don't manipulate string :

```
sqlConnection.getQueryBuilder(DummyObject.TYPE,
                                and(Constraints.equal(DummyObject.NAME, "hello"),
                                        Constraints.equal(DummyObject.ID, 1)))
                        .selectAll()
                        .getQuery()
                        .executeAsGlobs();
```


We create a public version of Globs for https://github.com/MarcGuiot/budgetview. 
This project pass away but the framework (or a private version of it) is used in 3 company.

Globs components :

To access a database : https://github.com/MarcGuiot/globs-db
To read/write json : https://github.com/MarcGuiot/globs-gson
to read/write xml : https://github.com/MarcGuiot/globs-xml (depends on https://github.com/MarcGuiot/saxstack)
to parse command line argument : https://github.com/MarcGuiot/globs-commandline
to read/write csv or similar format : https://github.com/MarcGuiot/csvExport (depends on apache csv)
a binary serializer (in the idea of protocol buffer) : https://github.com/MarcGuiot/globs-bin-serialisation
for http request using glob (produce an openApi json ) and a Glob api on etcd : https://github.com/MarcGuiot/globs-http (depends on apache http components)
to produce a view on data inside a glob or any where in it's child : https://github.com/MarcGuiot/globs-view
to implement a graphql api : https://github.com/MarcGuiot/globs-graphql (no dependency except antlr graphql.g4, do not response to query on schema, I use graphql-java for that.)


There is multiple way to create a GlobType.

## Example of a dynamically creation of GlobType 
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

Glob data = AType.TYPE.instance();

```


