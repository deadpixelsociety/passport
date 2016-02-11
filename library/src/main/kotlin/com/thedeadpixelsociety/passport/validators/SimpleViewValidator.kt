package com.thedeadpixelsociety.passport.validators

import android.view.View
import com.thedeadpixelsociety.passport.Rule
import com.thedeadpixelsociety.passport.ValidationResult

abstract class SimpleViewValidator<V : View, T> : ViewValidator<V, T> {
    private val rules = mutableSetOf<Rule<T>>()

    override fun reset(view: V) {
    }

    override fun rule(rule: Rule<T>) {
        rules.add(rule)
    }

    override fun rule(func: (T) -> Boolean, message: () -> String?) {
        rules.add(Rule(func, message))
    }

    override fun validate(view: V): Boolean {
        val value = getValue(view)
        var failure: ValidationResult? = null
        for (rule in rules) {
            val result = rule(value)
            if (!result.valid) {
                failure = result
                break
            }
        }

        return if (failure != null) {
            onFail(view, value, failure.message)
            false
        } else {
            onPass(view, value)
            true
        }
    }
}
