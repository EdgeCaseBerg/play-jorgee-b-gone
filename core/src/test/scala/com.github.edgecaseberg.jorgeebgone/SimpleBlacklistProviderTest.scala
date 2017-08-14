package com.github.edgecaseberg.jorgeebgone

import org.scalatest.FlatSpec

class SimpleBlacklistProviderTest extends FlatSpec {

	val blackList = SimpleBlacklistProvider(Vector(
		"Jorgee",
		"Banana"
	))

	behavior of "SimpleBlacklistProvider.contains"

	Seq(
		(true, "Pretend this is a user-agent string with Jorgee in it"),
		(true, "Banana!!!"),
		(false, "No blacklisted terms here"),
		// we're testing case sensitive here so:
		(false, "Pretend this is a user-agent string with jorgee in it"),
		(false, "banana!!!")
	).foreach {
			case (result, termToCheck) =>
				it should s"return ${result} for ${termToCheck}" in {
					assertResult(result)(blackList.contains(termToCheck))
				}
		}

	behavior of "SimpleBlacklistProvider.containsInsensitive"

	Seq(
		(true, "Pretend this is a user-agent string with Jorgee in it"),
		(true, "Banana!!!"),
		(false, "No blacklisted terms here"),
		(true, "Pretend this is a user-agent string with jorgee in it"),
		(true, "banana!!!")
	).foreach {
			case (result, termToCheck) =>
				it should s"return ${result} for ${termToCheck}" in {
					assertResult(result)(blackList.containsInsensitive(termToCheck))
				}
		}

}