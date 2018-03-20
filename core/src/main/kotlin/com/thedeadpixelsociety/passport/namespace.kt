package com.thedeadpixelsociety.passport

import android.app.Activity
import android.view.View
import android.view.ViewGroup

val EMPTY_STRING = ""

typealias Func0<R> = () -> R
typealias Action1<T> = (T) -> Unit
typealias Predicate<T> = (T) -> Boolean
typealias ValidatorFactory<V, T> = () -> Validator<V, T>

fun passport(func: Passport.() -> Passport) = Passport().let(func)

fun validator(func: Passport.() -> Passport) = PassportDelegate(func)

@Suppress("UNCHECKED_CAST")
internal fun <V : View> V.validatorTag() = getTag(R.id.passport_validator_tag) as? Validator<V, *>

internal fun rootView(activity: Activity): View? =
    activity.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)

internal fun walkViews(root: View?, action: Action1<View>) {
    if (root == null) return
    action(root)
    if (root is ViewGroup) {
        for (i in 0 until root.childCount) walkViews(root.getChildAt(i), action)
    }
}

internal fun allViews(root: View?, failFast: Boolean, predicate: Predicate<View>): Boolean {
    if (root == null) return false
    var res = predicate(root)
    if (!res && failFast) return false
    if (root is ViewGroup) {
        val childResults = arrayListOf<Boolean>()
        for (i in 0 until root.childCount) {
            val childResult = allViews(root.getChildAt(i), failFast, predicate)
            if (!childResult && failFast) return false
            childResults.add(childResult)
        }

        res = res && childResults.all { it }
    }

    return res
}