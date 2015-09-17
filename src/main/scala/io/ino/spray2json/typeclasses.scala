package io.ino.spray2json

trait NaturalTransformation[F[_], G[_]] {
  def apply[A](f: F[A]): G[A]
}
