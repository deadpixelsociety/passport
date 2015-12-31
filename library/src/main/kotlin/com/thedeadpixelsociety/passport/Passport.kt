package com.thedeadpixelsociety.passport

import kotlin.text.Regex
import android.view.View
import android.view.ViewGroup
import com.thedeadpixelsociety.passport.validators.ViewValidator

object Passport {
    object Rules {
        val emailRequired = Rule(email(), { "A valid email address is required." })
        // source: http://stackoverflow.com/a/123666
        val phoneRequired = Rule(matches("^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$"),
                { "A valid phone number is required." })
        val numbersOnly = Rule(numeric(), { "Only numbers may be used." })
        val lettersOnly = Rule(alpha(), { "Only letters may be used." })
        val alphanumericOnly = Rule(alpha(), { "Only letters and numbers may be used." })
    }

    fun matches(pattern: String) = matches(Regex(pattern))
    fun matches(regex: Regex): (View?, String?) -> Boolean = { view, text -> text?.matches(regex) ?: false }
    fun doesNotMatch(pattern: String) = doesNotMatch(Regex(pattern))
    fun doesNotMatch(regex: Regex): (View?, String?) -> Boolean = { view, text -> !(text?.matches(regex) ?: false) }

    fun notEmpty(): (View?, String?) -> Boolean = { view, text -> !text.isNullOrEmpty() }
    fun notBlank(): (View?, String?) -> Boolean = { view, text -> !text.isNullOrBlank() }

    fun length(min: Int = 0, max: Int = Int.MAX_VALUE): (View?, String?) -> Boolean = {
        view, text ->
        if (text != null) text.length >= min && text.length <= max else false
    }

    fun alpha() = matches("[a-zA-Z]*")
    fun alphanumeric() = matches("[a-zA-Z0-9]*")
    fun email() = matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    fun numeric() = matches("[0-9]*")
}

internal fun walkViews(root: View?, func: (View, ViewValidator<*>) -> Boolean): Boolean {
    var valid = true

    if (root != null) {
        val validator = root.validatorTag()
        if (validator != null) {
            valid = func(root, validator)
        } else if (root is ViewGroup) {
            val results = arrayListOf<Boolean>()

            for (i in 0..root.childCount)
                results.add(walkViews(root.getChildAt(i), func))

            valid = results.all { it }
        }
    }

    return valid
}
