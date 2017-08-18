package com.thedeadpixelsociety.passport

/**
 * Defines a basic validation rule for a specified type.
 * @param T The rule value type.
 * @param rule A [Predicate] that defines the rule logic. Returns true if the rule passes;
 * otherwise, false.
 * @param message The message generated when the rule fails. Used to describe the failure.
 */
data class Rule<in T>(private val rule: Predicate<T>, private val message: Func0<String?>) {
    /**
     * Invokes the rule for the given value.
     * @param value The value to test the rule against.
     * @return A [RuleResult] detailing if the rule passed or failed and a message describing the
     * failure, if any.
     */
    operator fun invoke(value: T) = rule(value).let { RuleResult(it, if (!it) message() else null) }
}