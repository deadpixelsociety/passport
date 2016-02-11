package com.thedeadpixelsociety.passport

import android.view.View
import com.thedeadpixelsociety.passport.validators.ViewValidator

fun passport(func: Passport.() -> Passport) = Passport().let(func)

@Suppress("UNCHECKED_CAST")
internal fun <V : View> V.validatorTag() = getTag(R.id.passport_validator_tag) as? ViewValidator<V, *>