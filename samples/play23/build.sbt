organization := "com.github.edgecaseberg"

name := "jorgee-b-gone-play23-sample"

version := "0.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val module = RootProject(file("../../play23"))
lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(module)