package com.thedeadpixelsociety.passport

object Functions {
    fun matches(regex: Regex): Predicate<String?> = { it?.let { regex.matches(it) } ?: false }
    fun matches(pattern: String) = matches(Regex(pattern))

    fun doesNotMatch(regex: Regex): Predicate<String?> = { !matches(regex)(it) }
    fun doesNotMatch(pattern: String) = doesNotMatch(Regex(pattern))

    val notEmpty: Predicate<String?> = { !it.isNullOrEmpty() }
    val notBlank: Predicate<String?> = { !it.isNullOrBlank() }

    val alpha = matches("[a-zA-Z]+")
    val numeric = matches("[0-9]+")
    val alphanumeric = matches("[a-zA-Z0-9]+")

    val email = matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun length(min: Int = 0, max: Int = Int.MAX_VALUE): Predicate<String?> = {
        (it?.length ?: 0) in min..max
    }
}

fun <T : String?> RuleCollection<T>.matches(pattern: String): RuleCollection<T> {
    rule(Functions.matches(pattern), { "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.matches(regex: Regex): RuleCollection<T> {
    rule(Functions.matches(regex), { "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.doesNotMatch(pattern: String): RuleCollection<T> {
    rule(Functions.doesNotMatch(pattern), { "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.doesNotMatch(regex: Regex): RuleCollection<T> {
    rule(Functions.doesNotMatch(regex), { "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.notEmpty(): RuleCollection<T> {
    rule(Functions.notEmpty, { "Value cannot be empty." })
    return this
}

fun <T : String?> RuleCollection<T>.notBlank(): RuleCollection<T> {
    rule(Functions.notBlank, { "Value cannot be blank." })
    return this
}

fun <T : String?> RuleCollection<T>.alpha(): RuleCollection<T> {
    rule(Functions.alpha, { "Value must contain only alpha characters." })
    return this
}

fun <T : String?> RuleCollection<T>.numeric(): RuleCollection<T> {
    rule(Functions.numeric, { "Value must contain only numeric characters." })
    return this
}

fun <T : String?> RuleCollection<T>.alphanumeric(): RuleCollection<T> {
    rule(Functions.alphanumeric, { "Value must contain only alphanumeric characters." })
    return this
}

fun <T : String?> RuleCollection<T>.email(): RuleCollection<T> {
    rule(Functions.email, { "Value must be a valid email address." })
    return this
}

fun <T : String?> RuleCollection<T>.length(min: Int, max: Int): RuleCollection<T> {
    rule(Functions.length(min, max), { "The value must be between $min and $max characters long." })
    return this
}