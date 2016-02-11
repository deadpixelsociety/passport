package com.thedeadpixelsociety.passport

import android.app.Activity
import android.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.thedeadpixelsociety.passport.validators.ViewValidator

class Passport {
    fun <V : View, T> with(view: V, validator: ViewValidator<V, T>, func: ViewValidator<V, T>.() -> Unit): Passport {
        validator.func()
        view.setTag(R.id.passport_validator_tag, validator)
        return this
    }

    fun reset(view: View) {
        walkViews(view, {
            it.validatorTag()?.reset(it)
            true
        })
    }

    fun reset(fragment: Fragment) = fragment.view?.let { reset(it) }

    fun reset(fragment: android.support.v4.app.Fragment) = fragment.view?.let { reset(it) }

    fun reset(activity: Activity) {
        val root = (activity.findViewById(android.R.id.content) as? ViewGroup)?.getChildAt(0)
        root?.let { reset(it) }
    }

    fun validate(view: View): Boolean {
        return walkViews(view, { validateView(it) })
    }

    fun validate(fragment: Fragment) = fragment.view?.let { validate(it) } ?: true

    fun validate(fragment: android.support.v4.app.Fragment) = fragment.view?.let { validate(it) } ?: true

    fun validate(activity: Activity): Boolean {
        val root = (activity.findViewById(android.R.id.content) as? ViewGroup)?.getChildAt(0)
        return root?.let { validate(it) } ?: true
    }

    private fun walkViews(root: View, func: (View) -> Boolean): Boolean {
        var valid = func(root)

        if (valid && root is ViewGroup) {
            val results = arrayListOf<Boolean>()
            for (i in 0..root.childCount - 1) {
                results.add(walkViews(root.getChildAt(i), func))
            }

            valid = results.all { it }
        }

        return valid
    }

    private fun validateView(view: View) = view.validatorTag()?.validate(view) ?: true
}
