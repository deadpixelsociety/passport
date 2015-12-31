package com.thedeadpixelsociety.passport

import android.app.Activity
import android.view.View
import android.view.ViewGroup

fun <A : Activity> A.clearErrors() = clearErrors(getContentRoot())
fun clearErrors(root: View?) {
    walkViews(root) {
        view, validator ->
        validator.error = null
        true
    }
}

fun <A : Activity> A.clearRules() = clearRules(getContentRoot())
fun clearRules(root: View?) {
    walkViews(root) {
        view, validator ->
        validator.clear()
        true
    }
}

fun <A : Activity> A.validateRules() = validateRules(getContentRoot())
fun validateRules(root: View?): Boolean {
    return walkViews(root, {
        view, validator ->
        validator.validate()
    })
}

fun <A : Activity> A.destroyValidators() = destroyValidators(getContentRoot())
fun destroyValidators(root: View?) {
    walkViews(root) {
        view, validator ->
        validator.destroy()
        true
    }
}

internal fun <A : Activity> A.getContentRoot() = (findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
