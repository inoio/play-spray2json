## play-spray2json

Convert [spray-json](https://github.com/spray/spray-json/) JsonFormat and RootJsonFormat classes to [play-json](https://www.playframework.com/documentation/2.3.x/ScalaJson) Format.

While in itself not terribly useful the test suite demonstrates how to use the excellent [spray-json-shapeless](https://github.com/fommil/spray-json-shapeless) library to convert hierachies of traits and case classes of arbitrary length to Json and back.

```scala
sealed trait SimpleTrait
case class Foo(s: String) extends SimpleTrait
case class Bar() extends SimpleTrait
case object Baz extends SimpleTrait
case class Faz(o: Option[String]) extends SimpleTrait
case class Recursive(l : List[SimpleTrait]) extends SimpleTrait
 ```
 
 and
 
 ```scala
 val st : SimpleTrait = Foo("hello")
 val r : SimpleTrait = Recursive(st :: Baz :: Bar() :: Nil)
 val result = Json.toJson(r)
 ```
 
 results in 
 
 ```json
 {"type":"Recursive","l":[{"type":"Foo","s":"hello"},{"type":"Baz"},{"type":"Bar"}]}
 ```
 
 and vice versa
 
## Motivation
 
The [spray-json-shapeless](https://github.com/fommil/spray-json-shapeless) uses shapeless to achieve the goal of (de-)serializing case classes and trait hierachies, which is a library I am not yet firm with. Hence this project just provides isomorphisms and natural transformations between the relevant datatypes.

## Documentation

See the test suite and especially read the documentation and caveats documented at [spray-json-shapeless](https://github.com/fommil/spray-json-shapeless)

## Thanks

Many thanks to [Sam](https://github.com/fommil) for sharing this awesome software.
  
## License

`play-spray2json` is [Free Software][free] under the Apache License v2.

 
 
 

