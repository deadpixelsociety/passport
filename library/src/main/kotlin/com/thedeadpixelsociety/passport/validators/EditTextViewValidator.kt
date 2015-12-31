package com.thedeadpixelsociety.passport.validators

import android.widget.EditText
import com.thedeadpixelsociety.passport.validators.ViewValidator

class EditTextViewValidator(view: EditText) : ViewValidator<EditText>(view) {
    override var error: String?
        get() = view()?.error?.toString()
        set(value) {
            view()?.error = value
        }

    override var text: String?
        get() = view()?.text?.toString()
        set(value) {
            view()?.setText(value)
        }
}