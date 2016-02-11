package com.thedeadpixelsociety.passport

data class Rule<T>(private val func: (T) -> Boolean, private val message: () -> String?) {
    operator fun invoke(value: T) = func(value).let { ValidationResult(it, if (!it) message() else null) }
}