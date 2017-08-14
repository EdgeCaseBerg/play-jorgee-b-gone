organization := "com.github.edgecaseberg"

name := "jorgee-b-gone-play24"

version := "0.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val module = RootProject(file("../core"))
lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(module)