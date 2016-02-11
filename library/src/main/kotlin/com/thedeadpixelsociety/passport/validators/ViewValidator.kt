package com.thedeadpixelsociety.passport.validators

import android.view.View
import com.thedeadpixelsociety.passport.Rule

interface ViewValidator<V : View, T> {
    fun getValue(view: V): T

    fun onFail(view: V, value: T, message: String?)

    fun onPass(view: V, value: T)

    fun reset(view: V)

    fun rule(rule: Rule<T>)

    fun rule(func: (T) -> Boolean, message: () -> String?)

    fun validate(view: V): Boolean
}