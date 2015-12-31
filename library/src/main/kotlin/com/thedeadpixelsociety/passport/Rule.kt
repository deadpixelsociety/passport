package com.thedeadpixelsociety.passport

import android.view.View
import com.thedeadpixelsociety.passport.validators.ViewValidator

data class Rule(val func: (View?, String?) -> Boolean, val message: () -> String?) {
    operator fun <V : View> invoke(validator: ViewValidator<V>) = func(validator.view(), validator.text)
}