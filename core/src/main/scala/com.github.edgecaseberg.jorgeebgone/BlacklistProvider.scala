package com.github.edgecaseberg.jorgeebgone

/** Trait to be implemented by black list providers */
abstract trait BlacklistProvider {

	/** A list of strings which contain keywords to look for in user agents that indicate that a string should be blocked
	 *  @note Implemented as a def so that extenders can declare it as a val for immutable operations or so one could have
	 *        a blacklist which loads from some external source.
	 */
	protected def blacklist: Vector[String]

	/** Convenience method to get a blacklist with all keywords lowercased
	 *  @note Implemented this way so that we only have to lowercase a blacklist
	 *        once per load in an extender.
	 */
	protected def lowercasedBlacklist: Vector[String] = blacklist.map(_.toLowerCase())

	/** Determine if a term has any blacklisted terms in it
	 *  @note This method is case sensitive, for case insensitive matching use [[containsInsensitive]]
	 *  @param termToCheck the term to check for the presence of blacklisted items in
	 *  @return true if the termToCheck contains any words from the blacklist
	 */
	def contains(termToCheck: String): Boolean = blacklist.exists(termToCheck.contains(_))

	/** Determine if a term has any blacklisted terms in it (case insensitive)
	 *  @note This method is case insensitive, for case sensitive matching use [[contains]]
	 *  @param termToCheck the term to check for the presence of blacklisted items in
	 *  @return true if the termToCheck contains any words from the blacklist
	 */
	def containsInsensitive(termToCheck: String) = {
		/* Do this only once then compare it against everything */
		val lowercasedTermToCheck = termToCheck.toLowerCase
		lowercasedBlacklist.exists(lowercasedTermToCheck.contains(_))
	}

}