package com.github.edgecaseberg

package object jorgeebgone {

	/** Exception to throw when one cannot load a blacklist for some reason
	 *  @param error The error message for this exception
	 *  @param cause The underlying cause of this exception if there is one.
	 */
	class BlacklistLoadingException(error: String, cause: Exception) extends RuntimeException(error, cause) {
		/** Create a BlacklistLoadingException with just an error message
		 *  @note the cause given to the underlying RuntimeException is null.
		 */
		def this(error: String) = this(error, null)
	}
}