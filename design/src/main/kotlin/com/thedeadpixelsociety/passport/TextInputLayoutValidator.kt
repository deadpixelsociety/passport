package com.thedeadpixelsociety.passport

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout

/**
 * Implements a validator for an Android design library [TextInputLayout]. Validation will be issued
 * against the child [TextInputEditText] and the error will be displayed on the layout itself.
 */
class TextInputLayoutValidator : BaseValidator<TextInputLayout, String>() {
    override fun value(target: TextInputLayout): String = target.editText?.text?.toString()
        ?: EMPTY_STRING

    override fun reset(target: TextInputLayout) {
        target.error = null
    }

    override fun passed(target: TextInputLayout, value: String) {
        target.error = null
    }

    override fun failed(target: TextInputLayout, value: String, messages: List<String>) {
        target.error = messages.joinToString(" ")
    }
}