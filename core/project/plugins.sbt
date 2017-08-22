addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.5.1")

resolvers ++= Seq(
	"Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.8")