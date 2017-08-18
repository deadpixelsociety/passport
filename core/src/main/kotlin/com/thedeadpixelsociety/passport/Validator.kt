package com.thedeadpixelsociety.passport

/**
 * Defines a basic object validiator.
 * @param V The type being validated (such as a View).
 * @param T The value type being validated (such as a string).
 */
interface Validator<in V, T> : RuleCollection<T> {
    /**
     * Invokes all rules in the current validator and returns a value determining if they passed or
     * failed.
     * @param target The target to validate.
     * @param method The [ValidationMethod] to use.
     * @return true if all validation rules passed; otherwise, false.
     */
    fun validate(target: V, method: ValidationMethod): Boolean

    /**
     * Retrieves the current target value.
     * @param target The target to query.
     * @return The current value of the target.
     */
    fun value(target: V): T

    /**
     * Resets the target to an unvalidated state.
     * @param target The target to reset.
     */
    fun reset(target: V)

    /**
     * Called when the target has passed validation.
     * @param target The target object.
     * @param value The value that passed validation.
     */
    fun passed(target: V, value: T)

    /**
     * Called when the target has failed validation.
     * @param target The target object.
     * @param value The value that failed validation.
     * @param messages The failure messages, if any.
     */
    fun failed(target: V, value: T, messages: List<String>)
}