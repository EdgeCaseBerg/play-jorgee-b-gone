package com.github.edgecaseberg.jorgeebgone

import org.scalatest.FlatSpec

class ImmutableBlacklistProviderTest extends FlatSpec {

	class NumCallsBlackList extends ImmutableBlacklistProvider {
		@volatile var numCalls = 0
		override def blacklist = {
			this.numCalls = numCalls + 1
			Vector("A")
		}
	}

	def testCase(code: NumCallsBlackList => Unit) {
		val blacklist = new NumCallsBlackList()
		code(blacklist)
	}

	"ImmutableBlacklistProvider" should "only create the blacklist once" in testCase { blacklist =>
		blacklist.contains("A")
		blacklist.contains("A")
		blacklist.contains("A")
		assertResult(1)(blacklist.numCalls)
	}

	it should "only create the lowercased blaclist once" in testCase { blacklist =>
		blacklist.containsInsensitive("a")
		blacklist.containsInsensitive("a")
		blacklist.containsInsensitive("a")
		assertResult(1)(blacklist.numCalls)
	}
}