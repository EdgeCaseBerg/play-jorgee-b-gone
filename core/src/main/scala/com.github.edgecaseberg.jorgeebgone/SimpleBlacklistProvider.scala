package com.github.edgecaseberg.jorgeebgone

/** Simple blacklist provider that constructs a blacklist from a Vector of strings */
case class SimpleBlacklistProvider(
	override val blacklist: Vector[String]
) extends ImmutableBlacklistProvider