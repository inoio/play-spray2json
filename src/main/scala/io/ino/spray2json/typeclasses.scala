package io.ino.spray2json

trait NaturalTransformation[F[_], G[_]] {
  def apply[A](f: F[A]): G[A]
}

trait Iso[A,B] {
  def to(a: A) : B
  def from(b: B) : A
}

object Iso {
  def apply[A,B](implicit iso : Iso[A,B]) = iso
  implicit def to[A,B](value: A)(implicit iso : Iso[A,B]) : B  = iso.to(value)
  implicit def from[A,B](value: B)(implicit iso : Iso[A,B]) : A = iso.from(value)
}
