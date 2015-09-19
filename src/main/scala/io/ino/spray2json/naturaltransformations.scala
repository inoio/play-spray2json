package io.ino
package spray2json

import spray.json._
import play.api.libs.json.{Format, JsResult, JsSuccess, JsUndefined => PJsUndefined}
import play.api.libs.json.{JsValue => PJsValue, JsObject => PJsObject, JsArray => PJsArray}
import play.api.libs.json.{JsString => PJsString, JsNumber => PJsNumber, JsBoolean => PJsBoolean, JsNull => PJsNull}

trait LowLevelImplicitsIso {
  implicit object JsValueIso extends Iso[JsValue, PJsValue] {
    def to(jsValue: JsValue) : PJsValue = {
      jsValue match {
        case JsObject(fields) => PJsObject(fields mapValues to toSeq)
        case JsArray(elements) => PJsArray(elements map to )
        case JsString(s) => PJsString(s)
        case JsNumber(nr) => PJsNumber(nr)
        case JsBoolean(b) => PJsBoolean(b)
        case JsNull => PJsNull
      }
    }

    def from(jsValue: PJsValue) : JsValue = {
      jsValue match {
        case PJsObject(fields) => JsObject(fields map { case (name, jsValue) => (name, from(jsValue)) } toMap)
        case PJsArray(elements) => JsArray(elements map from toVector )
        case PJsString(s) => JsString(s)
        case PJsNumber(nr) => JsNumber(nr)
        case PJsBoolean(b) => JsBoolean(b)
        case PJsNull => JsNull
        case _ : PJsUndefined => JsNull // ???
      }
    }
  }
}

trait LowLevelImplicitsNat extends LowLevelImplicitsIso {
  
  type ~>[F[_], G[_]] = NaturalTransformation[F,G]
  
  /**
   * Natural transformation from spray to play formats.
   */
  implicit object formatRootJsonFormat extends (RootJsonFormat ~> Format) {
    import Iso._
    
    def apply[A](rjf : RootJsonFormat[A]) : Format[A] = new Format[A] {
      def writes(o: A): PJsValue = {
        rjf.write(o)
      }
      
      def reads(jsValue: PJsValue) : JsResult[A] = {
        val result = rjf.read(jsValue)
        JsSuccess(result)  
      }
    }
  }
  
  
  /**
   * Natural transformation from spray to play formats.
   */
  implicit object formaJsonFormat extends (JsonFormat ~> Format) {
    import Iso._
    def apply[A](rjf : JsonFormat[A]) : Format[A] = new Format[A] {
      def writes(o: A): PJsValue = {
        rjf.write(o)
      }
      
      def reads(jsValue: PJsValue) : JsResult[A] = {
        val result = rjf.read(jsValue)
        JsSuccess(result)  
      }
    }
  }
  
  
  /**
   * implicit conversion from spray to play formats.
   */
  implicit def sprayJsonFormat2Play[A](implicit P: JsonFormat[A], ev: JsonFormat ~> Format): Format[A] = {
    ev(P)
  }
  
}


object isos extends LowLevelImplicitsIso
/**
 * import io.nio.spray2json.allconversions._ to get going.
 */
object allconversions extends LowLevelImplicitsNat
