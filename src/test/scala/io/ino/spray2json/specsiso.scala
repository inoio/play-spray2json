package io.ino
package spray2json

import org.specs2.mutable._
import spray.json.JsTrue

class IsoSpec extends Specification {
  import isos._
  import Iso._
  // import spray.json._
  
  import play.api.libs.json.Json
  
  def roundTrip(sJsValue: spray.json.JsValue, pJsValue: play.api.libs.json.JsValue)(implicit iso : Iso[spray.json.JsValue, play.api.libs.json.JsValue]) = {
    sJsValue must beEqualTo(iso.from(iso.to(sJsValue)))
    pJsValue must beEqualTo(iso.to(iso.from(pJsValue)))
  }
  
  "Isos" should {
    "transform play json to spray json and vice verso" in {
      val playJson = Json.obj("bool" -> true, 
          "number" -> 1, 
          "obj" -> Json.obj("string" -> "Hello", "arr" -> Json.arr(1,2,3)))
      val sprayJson : spray.json.JsValue = spray.json.JsObject(
          "bool" -> JsTrue, 
          "number" -> spray.json.JsNumber(1),
          "obj" -> spray.json.JsObject(
              "string" -> spray.json.JsString("Hello"), 
              "arr" -> spray.json.JsArray(spray.json.JsNumber(1), spray.json.JsNumber(2), spray.json.JsNumber(3))
          )
      )
      roundTrip(sprayJson, playJson)
    }
  }
  
}