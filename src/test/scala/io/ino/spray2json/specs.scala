package io.ino
package spray2json

import org.specs2.mutable._
import shapeless._

import spray.json._

import fommil.sjs._
import allconversions._
import play.api.libs.json.{Json, Format}
import allconversions._

class JsonSpec extends Specification {
  import traits._
  import Implicits._

  val st : SimpleTrait = Foo("hello")
  val r : SimpleTrait = Recursive(st :: Baz :: Bar() :: Nil)

  def roundTrip[A : Format](value: A, string : String) = {
    val result = Json.toJson(value)
    result.toString must beEqualTo(string)
    Json.parse(string).as[A] must beEqualTo(value)
  }

  
  "The play-json (de-)serializer" should {
    "be able to (de-)serialize the class Foo" in {
      roundTrip(st, """{"type":"Foo","s":"hello"}""")
    }
    
    "be able to (de-)serialize the class Recursive" in {
      roundTrip(r, """{"type":"Recursive","l":[{"type":"Foo","s":"hello"},{"type":"Baz"},{"type":"Bar"}]}""")
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
