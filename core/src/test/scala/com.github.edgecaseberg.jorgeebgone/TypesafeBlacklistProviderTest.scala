package com.github.edgecaseberg.jorgeebgone

import org.scalatest.FlatSpec
import com.typesafe.config.{ Config, ConfigFactory }

class TypesafeBlacklistProviderTest extends FlatSpec {

	def defaultTestCase(key: String)(code: TypesafeBlacklistProvider => Unit) {
		val blacklist = new TypesafeBlacklistProvider(key)
		code(blacklist)
	}

	def configProvidedTestCase(key: String, config: Config)(code: TypesafeBlacklistProvider => Unit) {
		val blacklist = new TypesafeBlacklistProvider(key, config)
		code(blacklist)
	}

	"TypesafeBlacklistProvider" should "load configuration from the reference.conf by default" in {
		defaultTestCase("jorgeebgone.blacklist") { blacklist =>
			assert(blacklist.contains("Jorgee"))
		}
	}

	it should "fail if the key to load does not exist" in {
		intercept[BlacklistLoadingException] {
			defaultTestCase("crap") { blacklist =>
				fail("Should not have reached this")
			}
		}
	}

	it should "load a blacklist from a given Config" in {
		val config = ConfigFactory.parseString("""{"custom":["A", "B", "C"]}""")
		configProvidedTestCase("custom", config) { blacklist =>
			assert(blacklist.contains("A"))
			assert(blacklist.contains("B"))
			assert(blacklist.contains("C"))
		}
	}
}