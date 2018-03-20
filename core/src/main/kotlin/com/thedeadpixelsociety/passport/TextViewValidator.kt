package com.thedeadpixelsociety.passport

import android.widget.TextView

/**
 * Implements a validator for an Android [TextView]. This will utilize the view's validatorFactory error
 * capabilities to display validation errors.
 */
class TextViewValidator : BaseValidator<TextView, String>() {
    override fun value(target: TextView): String = target.text?.toString() ?: EMPTY_STRING

    override fun passed(target: TextView, value: String) {
        target.error = null
    }

    override fun failed(target: TextView, value: String, messages: List<String>) {
        target.error = messages.joinToString(" ")
    }

    override fun reset(target: TextView) {
        target.error = null
    }
}