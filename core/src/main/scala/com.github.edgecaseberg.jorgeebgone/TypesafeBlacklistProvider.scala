package com.github.edgecaseberg.jorgeebgone

import scala.collection.JavaConverters._
import scala.collection.immutable.VectorBuilder
import com.typesafe.config.{ ConfigFactory, Config }

/** BlacklistProvider that uses a Config from typesafe and a key to load a list of blacklist strings
 *
 *  @note The reference.conf file provided by this library defines a default set at jorgeebgone.blacklist
 *  @param blacklistKeyForStringList The key to retrieve from the given configuration
 *  @param config The Configuration to provide the list of strings for the blacklist
 */
class TypesafeBlacklistProvider(
		blacklistKeyForStringList: String,
		config: Config = ConfigFactory.load()
) extends ImmutableBlacklistProvider {

	/** Override the blacklist method from BlacklistProvider with a val so we only compute it once */
	override protected val blacklist: Vector[String] = {
		if (!config.hasPath(blacklistKeyForStringList)) {
			throw new BlacklistLoadingException(s"Key ${blacklistKeyForStringList} did not exist in the given config")
		}
		val javaStringList = config.getStringList(blacklistKeyForStringList)
		val vectorBuilder = new VectorBuilder[String]()
		for (str <- javaStringList.asScala) {
			vectorBuilder += str
		}
		vectorBuilder.result()
	}

}