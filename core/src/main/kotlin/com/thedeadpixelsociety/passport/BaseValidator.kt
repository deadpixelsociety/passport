package com.thedeadpixelsociety.passport

/**
 * Implements basic functionality of the [Validator] interface.
 */
abstract class BaseValidator<in V, T> : Validator<V, T> {
    private val rules = hashSetOf<Rule<T>>()

    override fun rules(): List<Rule<T>> = rules.toList()

    override fun rule(rule: Rule<T>): RuleCollection<T> {
        rules.add(rule)
        return this
    }

    override fun rule(rule: Predicate<T>, message: Func0<String?>) = rule(Rule(rule, message))

    override fun validate(target: V, method: ValidationMethod): Boolean {
        val value = value(target)

        var passed = true
        val messages = hashSetOf<String>()
        for (rule in rules) {
            val res = rule(value)
            if (res.passed) continue
            passed = false
            if (res.message != null && !res.message.isEmpty()) messages.add(res.message)
            if (method == ValidationMethod.IMMEDIATE) break
        }

        if (passed) passed(target, value)
        else failed(target, value, messages.toList())

        return passed
    }

    override fun reset(target: V) {

    }

    override fun passed(target: V, value: T) {
    }

    override fun failed(target: V, value: T, messages: List<String>) {
    }
}