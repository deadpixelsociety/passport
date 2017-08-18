package com.thedeadpixelsociety.passport

/**
 * The result of an invocation of a [Rule].
 * @param passed true if the rule passed; otherwise, false.
 * @param message An optional message to describe the rule failure, if any.
 */
data class RuleResult(val passed: Boolean, val message: String?)
