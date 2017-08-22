Jorgee B Gone [![Build Status](https://travis-ci.org/EdgeCaseBerg/play-jorgee-b-gone.svg?branch=master)](https://travis-ci.org/EdgeCaseBerg/play-jorgee-b-gone)

Jorgee B Gone
=============================================================

Tired of seeing this crap in your access log?

```
my.ip.add.r – – [SomeDate] “HEAD http://ip.addr:80/1phpmyadmin/ HTTP/1.1″ 404 219 “-” “Mozilla/5.0 Jorgee”
```

Redirect the scanner somewhere else with Jorgee B Gone!


### Configuration

In your application.conf set two keys

```
jorgeebgone {
	# Where are you sending Jorgee to?
	redirectTo = "https://hell.com"

	# Whom are you sending there?
	blacklist = [
		"Jorgee",
		"nsis_inetc (mozilla)",
		"nsis_inetc",
		"RookIE/1.0"
	]
}
```

The `jorgeebgone.redirectTo` key is where you'll be redirecting the scanner 
and the `jorgeebgone.blacklist` is the list of strings to look for in a 
user agent header to determine that you should redirect them. Try not to be 
too broad in what you put here otherwise you'll redirect legitimate traffic!

### Before you setup 

Publish the module you need and use it. 

```
cd play23 && sbt publishLocal
OR
cd play24 && sbt publishLocal
```

Then you can update your `project/plugings.sbt` to include it

play 2.3:
```
libraryDependencies ++= Seq(
	"com.github.edgecaseberg" %% "jorgee-b-gone-play23" % "0.0.0"
)
```

play 2.4:
```
libraryDependencies ++= Seq(
	"com.github.edgecaseberg" %% "jorgee-b-gone-play24" % "0.0.0"
)
```

### Installing

You can check out the samples for code examples if you learn that way, but the 
rough install process is like so:

#### Play 2.3 

1. Enable The plugin in conf/play.pluggins `10000:plugins.JorgeeBGonePlugin` (You can pick any number, the plugin only depends on an implicit application and a configuration being present)
2. Update your Global object to use the filter via `WithFilters(..., JorgeeBGonePlugin.filter)`

2.5 If you don't have a global object already make one:
```
import play.api.mvc._

import plugins.JorgeeBGonePlugin

object Global extends WithFilters(JorgeeBGonePlugin.filter)

```
and update your conf to set it `application.global=Global`

#### Play 2.4

1. Enable the module in your .conf file via `play.modules.enabled  += "module.JorgeeBGoneModule"`
2. If you already have a `HttpFilters` class defined, make it take a `JorgeeBGoneUserAgentFilter` as a parameter and pass it to the filters seq you've made.

2.5 If you don't have a filter defined, create on in your default package like so:
```
import javax.inject.Inject
import play.api.http.HttpFilters

import module.JorgeeBGoneUserAgentFilter

class Filters @Inject() (
	jorgeeBGoneUserAgentFilter: JorgeeBGoneUserAgentFilter
) extends HttpFilters {

  val filters = Seq(jorgeeBGoneUserAgentFilter)
}
```

Enjoy!

