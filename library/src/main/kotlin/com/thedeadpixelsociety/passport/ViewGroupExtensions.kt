package com.thedeadpixelsociety.passport

import android.view.ViewGroup

fun <V : ViewGroup> V.clearErrors() {
    walkViews(this) {
        view, validator ->
        validator.error = null
        true
    }
}

fun <V : ViewGroup> V.clearRules() {
    walkViews(this) {
        view, validator ->
        validator.clear()
        true
    }
}

fun <V : ViewGroup> V.validateRules(): Boolean {
    return walkViews(this) {
        view, validator ->
        validator.validate()
    }
}

fun <V : ViewGroup> V.destroyValidators() {
    walkViews(this) {
        view, validator ->
        validator.destroy()
        true
    }
}