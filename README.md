# GlobsFramework
This is a metamodel framework.

The goal of this framework is to replace beans by an object called a Glob. This Glob is a kind of map. The key to access a field 
is not a string but an instance of a Field. Theses Fields (for a given 'bean') are group in an instance of a GlobType. GlobType and Field 
contains additional information called annotation (like java annotation but as Globs, of course ;-) ).

Given a Glob, it's GlobType is known. The GlobType give access to the fields. A real field can be visited using the visitor pattern.
Real fields are IntegerField and IntegerArrayField, DoubleField, GlobField, UnionField, etc.
A glob keep the fact that a field is set or not. By default accessing an unset field return null. 

Thanks to that, we can write generic code (not magic;-) that serialize in XML/Json/binary,
that insert in a DB, compare them self, and many more.

Most of the code that use introspection to achieve there work can be replaced by using GlobType. As a GlobType 
expose a way to access field, and that the field have a known type, the written code is fully control.
Actually, a GlobType is a model for data and only data. In java we use classes to encapsulate data (records now), it is easy to understand that
code that depends on data like serialisation, db access, etc is really easier to write with Globs as we don't have the complexity 
of the many way to access and use a class compared to a Glob. And it is more natural writing code using an interface then using introspection!

So, if GlobsFramework is useful everywhere input/output is used, it is also great inside our own code.
Here are some usage example :
* Any code that must control flow of data, of the configuration, etc.
* Any code that is configured using user input.
* If you want generic way to filter or transform data.

A JavaScript (TS) version exist, it allow the dynamic creation of screen to create configuration in React. But the code is not open. 

It's main drawback is the fact that it mixed badly with beans.
The advantage is that GlobsFramework is open source, very small, without dependency (only slf4j for logging) and is easy to maintain.

## history
The idea came from telecom industry where many model were built using GDMO (https://en.wikipedia.org/wiki/Guidelines_for_the_Definition_of_Managed_Objects).
This model was read and used to generate code (for DB, UI, BER Asn1 encoding, etc). These generated object was called Managed Objects (MO).
At Nortel, Xml was used to describe this Managed Object (MO). At the beginning, this generic model was used to generate code, but soon it has been used directly in the code.

Regis Medina and I rewrote a second version for a private financial company we work for (it was in 2006 and the framework is still used)
Later, we wrote an open source third version for our own project (BudgetView https://web.archive.org/web/20181229134134/http://www.mybudgetview.com/, https://github.com/MarcGuiot/budgetview)

These version is used in few company we worked for.

## exemple of a db query

```
sqlConnection.getQueryBuilder(DummyObject.TYPE,
                                and(Constraints.equal(DummyObject.NAME, "hello"),
                                        Constraints.equal(DummyObject.ID, 1)))
                        .selectAll()
                        .getQuery()
                        .executeAsGlobs();
```


## components

Today's Globs components :

* To access a database : https://github.com/MarcGuiot/globs-db
* To read/write json : https://github.com/MarcGuiot/globs-gson
* to read/write xml : https://github.com/MarcGuiot/globs-xml (depends on https://github.com/MarcGuiot/saxstack)
* to parse command line argument : https://github.com/MarcGuiot/globs-commandline
* to read/write csv or similar format : https://github.com/MarcGuiot/csvExport (depends on apache csv)
* a binary serializer (in the idea of protocol buffer) : https://github.com/MarcGuiot/globs-bin-serialisation
* for http request using glob (produce an openApi json ) and a Glob api on etcd : https://github.com/MarcGuiot/globs-http (depends on apache http components)
* to produce a view on data inside a glob or any where in it's child : https://github.com/MarcGuiot/globs-view
* to implement a graphql api : https://github.com/MarcGuiot/globs-graphql (no dependency except antlr graphql.g4, do not response to query on schema, I use graphql-java for that.)


A GlobType interface:
```
public interface GlobType {
    String getName();
    Field[] getFields();
    Field getField(String name) throws ItemNotFound;
    MutableGlob instantiate();
    Glob getAnnotation(Key key);

    ...
```

A Field interface
```
public interface Field {
   GlobType getGlobType()
   String getName();
   <T extends FieldVisitor> T visit(T visitor) throws Exception;
   Glob getAnnotation(Key key);
   ...
}

public interface StringField extends Field {
};

...
public interface GlobField extends Field {
  GlobType getTargetType();
};
...
```


A Glob interface:
```
public interface Glob {
    GlobType getType();
    boolean isSet(Field field) throws ItemNotFound;
    boolean isNull(Field field) throws ItemNotFound;
    Object getValue(Field field) throws ItemNotFound;
    Double get(DoubleField field) throws ItemNotFound;
    double get(DoubleField field, double valueIfNull) throws ItemNotFound;
    Optional<Double> getOpt(DoubleField field);
    Integer getValue(IntegerField field) throws ItemNotFound;
    ...
    Glob get(GlobField field) throws ItemNotFound;
    ...
    Glob[] get(GlobArrayField field) throws ItemNotFound;
    ...
```

A mutable Glob :
```
public insterface MutableGlob extends Glob {
   MutableGlob set(DoubleField field, Double value);
   void unset(Field field);
   ...
```

To create a GlobType (used in the json deserialization of a GlobType for exemple)
In these example, we create a GlobType, associate an 'annotation' called NamingField to a field, set and get a value for the given field,
and retreive the field using the NamingFieldAnnotationType which is also a Glob.


```
         GlobType type = GlobTypeBuilderFactory.create("product")
            .addLongField("id")
            .addStringField("title", NamingFieldAnnotationType.INSTANCE)
            .addStringField("handle")
            .addDoubleField("price")
            .addBooleanField("published")
            .get();

        MutableGlob data = type.instantiate();

        StringField titleField = type.getTypedField("title");
        data.set(titleField, "XPhone");

        assertEquals("XPhone", data.get(titleField));

        Field namingField = data.getType().findFieldWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY);
        assertEquals("XPhone", data.getValue(namingField));
```

## static way when the type is known
```
public static class ProductType {
   public static GlobType TYPE;
   
   public static LongField id;

   public static StringField title;
   
   public static DoubleField price;
   
   public static BooleanField published;
   
   static {
      GlobTypeLoaderFactory.init(ProductType.class, "Product").load();
   }
}

MutableGlob data = ProductType.TYPE.instance();
data.set(ProductType.id, 43235)
    .set(ProductType.title, "XPhone")
    .set(ProductType.price, 1599.);

...

```

With the static initialisation we have the best of two world.

The dynamic part for generic code : 
```
Glob g = GSonUtils.decode("{'id': 43235, 'title': 'XPhone'}", ProductType.TYPE);
```

The static part when you know the attribut you want : 
```
String title = g.get(ProductType.title)
assertEquals("XPhone", title);
```
