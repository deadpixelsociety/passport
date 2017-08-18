package com.thedeadpixelsociety.passport

/**
 * A collection of [Rule] definitions.
 */
interface RuleCollection<T> {
    /**
     * The current collection of rules.
     * @return A list of rules.
     */
    fun rules(): List<Rule<T>>

    /**
     * Adds a new [Rule] to the collection.
     * @param rule The [Rule] to add.
     * @return The current collection.
     */
    fun rule(rule: Rule<T>): RuleCollection<T>

    /**
     * Adds a new [Rule] to the collection.
     * @param rule A [Predicate] that defines the rule logic. Returns true if the rule passes;
     * otherwise, false.
     * @param message The message generated when the rule fails. Used to describe the failure.
     * @return The current collection.
     */
    fun rule(rule: Predicate<T>, message: Func0<String?>): RuleCollection<T>
}