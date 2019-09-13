package com.thedeadpixelsociety.passport

import androidx.fragment.app.Fragment

/**
 * Resets validation for the target fragment's view.
 * @param fragment The [Fragment] to reset.
 */
fun Passport.reset(fragment: Fragment) = fragment.view?.let { reset(it) }

/**
 * Validates the specified support fragment and all child views.
 * @param fragment The support [Fragment] to validate.
 * @param method The [ValidationMethod] to use. Defaults to [ValidationMethod.BATCH].
 * @return true if validation passed; otherwise, false.
 */
fun Passport.validate(fragment: Fragment, method: ValidationMethod = ValidationMethod.BATCH)
        = fragment.view?.let { validate(it, method) } ?: true
