package com.github.edgecaseberg.jorgeebgone

/** Trait to be implemented by black list providers who will not need to modify their blacklist at runtime */
abstract trait ImmutableBlacklistProvider extends BlacklistProvider {

	/** Memoize the call the lowercasedBlacklist since we know blacklist does not change */
	private lazy val memoizedLowercasedBlacklist = lowercasedBlacklist
	private lazy val memoizedBlacklist = blacklist

	override def contains(termToCheck: String): Boolean = memoizedBlacklist.exists(termToCheck.contains(_))

	/** Override the containsInsensitive to take advantage of the memoized lowercase blacklist */
	override def containsInsensitive(termToCheck: String) = {
		/* Do this only once then compare it against everything */
		val lowercasedTermToCheck = termToCheck.toLowerCase
		memoizedLowercasedBlacklist.exists(lowercasedTermToCheck.contains(_))
	}

}