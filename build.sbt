name := "play-spray2json"

organization := "io.ino"

licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://github.com/inoio/play-spray2json"))

scalaVersion := "2.11.7"

typelevelDefaultSettings

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json"    % "2.3.10",
  "io.spray"          %%  "spray-json"  % "1.3.2",
  "org.specs2"        %% "specs2-core"  % "3.6.4"   % "test",
  "com.github.fommil" %% "spray-json-shapeless" % "1.1.0" % "test",
  "org.slf4j" 		  %  "slf4j-nop"    % "1.7.12" % "test"
)

// Maven publishing info
publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>https://github.com/inoio/play-spray2json</url>
  <scm>
    <url>git@github.com:inoio/play-spray2json.git</url>
    <connection>scm:git:git@github.com:inoio/play-spray2json.git</connection>
  </scm>
  <developers>
    <developer>
      <id>markus.klink</id>
      <name>Markus Klink</name>
      <url>https://github.com/justjoheinz</url>
    </developer>
  </developers>)