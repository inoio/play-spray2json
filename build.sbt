name := "play-spray2json"

organization := "inoio"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json"    % "2.3.10",
  "io.spray"          %%  "spray-json"  % "1.3.2",
  "org.specs2"        %% "specs2-core"  % "3.6.4"   % "test",
  "com.github.fommil" %% "spray-json-shapeless" % "1.1.0" % "test"
)
