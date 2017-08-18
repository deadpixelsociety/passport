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

fun <T : String?> RuleCollection<T>.matches(pattern: String, message: String? = null): RuleCollection<T> {
    rule(Functions.matches(pattern), { message ?: "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.matches(regex: Regex, message: String? = null): RuleCollection<T> {
    rule(Functions.matches(regex), { message ?: "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.doesNotMatch(pattern: String, message: String? = null): RuleCollection<T> {
    rule(Functions.doesNotMatch(pattern), { message ?: "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.doesNotMatch(regex: Regex, message: String? = null): RuleCollection<T> {
    rule(Functions.doesNotMatch(regex), { message ?: "Invalid value." })
    return this
}

fun <T : String?> RuleCollection<T>.notEmpty(message: String? = null): RuleCollection<T> {
    rule(Functions.notEmpty, { message ?: "Value cannot be empty." })
    return this
}

fun <T : String?> RuleCollection<T>.notBlank(message: String? = null): RuleCollection<T> {
    rule(Functions.notBlank, { message ?: "Value cannot be blank." })
    return this
}

fun <T : String?> RuleCollection<T>.required(message: String? = null): RuleCollection<T> {
    rule(Functions.notBlank, { message ?: "A value is required." })
    return this
}

fun <T : String?> RuleCollection<T>.alpha(message: String? = null): RuleCollection<T> {
    rule(Functions.alpha, { message ?: "Value must contain only alpha characters." })
    return this
}

fun <T : String?> RuleCollection<T>.numeric(message: String? = null): RuleCollection<T> {
    rule(Functions.numeric, { message ?: "Value must contain only numeric characters." })
    return this
}

fun <T : String?> RuleCollection<T>.alphanumeric(message: String? = null): RuleCollection<T> {
    rule(Functions.alphanumeric, { message ?: "Value must contain only alphanumeric characters." })
    return this
}

fun <T : String?> RuleCollection<T>.email(message: String? = null): RuleCollection<T> {
    rule(Functions.email, { message ?: "Value must be a valid email address." })
    return this
}

fun <T : String?> RuleCollection<T>.length(min: Int, max: Int, message: String? = null): RuleCollection<T> {
    rule(Functions.length(min, max), { message ?: "The value must be between $min and $max characters long." })
    return this
}