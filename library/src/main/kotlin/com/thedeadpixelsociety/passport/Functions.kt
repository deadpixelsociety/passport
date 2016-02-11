package com.thedeadpixelsociety.passport

object Functions {
    fun matches(pattern: String) = matches(Regex(pattern))
    fun matches(regex: Regex): (String?) -> Boolean = { it?.let { regex.matches(it) } ?: false }

    fun doesNotMatch(pattern: String) = doesNotMatch(Regex(pattern))
    fun doesNotMatch(regex: Regex): (String?) -> Boolean = { !matches(regex)(it) }

    val notEmpty: (String?) -> Boolean = { !it.isNullOrEmpty() }
    val notBlank: (String?) -> Boolean = { !it.isNullOrBlank() }

    val alpha = matches("[a-zA-Z]+")
    val numeric = matches("[0-9]+")
    val alphanumeric = matches("[a-zA-Z0-9]+")

    val email = matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun length(value: String?, min: Int = 0, max: Int = Int.MAX_VALUE) = value?.let { it.length >= min && it.length <= max } ?: false
}