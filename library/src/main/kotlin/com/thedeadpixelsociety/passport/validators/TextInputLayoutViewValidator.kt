package com.thedeadpixelsociety.passport.validators

import android.support.design.widget.TextInputLayout

class TextInputLayoutViewValidator(view: TextInputLayout) : ViewValidator<TextInputLayout>(view) {
    override var error: String?
        get() = view()?.error?.toString()
        set(value) {
            view()?.error = value
        }

    override var text: String?
        get() = view()?.editText?.text?.toString()
        set(value) {
            view()?.editText?.setText(value)
        }
}
