organization := "com.github.edgecaseberg"

name := "jorgee-b-gone"

version := "0.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"com.typesafe" % "config" % "1.3.1",
	"org.scalatest" %% "scalatest" % "3.0.0" % "test",
	"org.mockito" % "mockito-all" % "1.10.+" % "test"
)