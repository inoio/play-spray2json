package io.ino
package spray2json

import org.specs2.mutable._
import shapeless._

import spray.json._

import fommil.sjs._
import allconversions._
import play.api.libs.json.Json
import allconversions._

class JsonSpec extends Specification {
  import traits._
  import Implicits._

  val st : SimpleTrait = Foo("hello")
  val r : SimpleTrait = Recursive(st :: Baz :: Bar() :: Nil)
  
  "The Json serializer" should {
    "be able to serialize the class Foo" in {
      val result = Json.toJson(st)
      result.toString must beEqualTo("""{"type":"Foo","s":"hello"}""")
    }
    
     "be able to serialize the class Recursive" in {
      val result = Json.toJson(r)
      result.toString must beEqualTo("""{"type":"Recursive","l":[{"type":"Foo","s":"hello"},{"type":"Baz"},{"type":"Bar"}]}""")
    }
    
  }
  
  "The Json deserializer" should {
    "be able to deserialize the class Foo" in {
      val result = Json.parse("""{"type":"Foo","s":"hello"}""").as[SimpleTrait]
      result must beEqualTo(st)
    }
    
    "be able to deserialize the class Recursive" in {
      val result = Json.parse("""{"type":"Recursive","l":[{"type":"Foo","s":"hello"},{"type":"Baz"},{"type":"Bar"}]}""").as[SimpleTrait]
      result must beEqualTo(r)
    }
  }

}

package traits {
  sealed trait SimpleTrait
  case class Foo(s: String) extends SimpleTrait
  case class Bar() extends SimpleTrait
  case object Baz extends SimpleTrait
  case class Faz(o: Option[String]) extends SimpleTrait
  case class Recursive(l : List[SimpleTrait]) extends SimpleTrait

}

object Implicits extends DefaultJsonProtocol with FamilyFormats {
  import traits._
  
  implicit val SimpleTraitFormat : RootJsonFormat[SimpleTrait] = cachedImplicit
 }