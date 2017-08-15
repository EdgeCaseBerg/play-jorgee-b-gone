organization := "com.github.edgecaseberg"

name := "jorgee-b-gone-play23"

version := "0.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val module = RootProject(file("../core"))
lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(module)

libraryDependencies ++= Seq(
	"org.scalatestplus" %% "play" % "1.2.0" % "test"
)