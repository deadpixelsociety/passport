package com.thedeadpixelsociety.passport.validators

import android.widget.EditText

class EditTextValidator : SimpleViewValidator<EditText, String?>() {
    override fun getValue(view: EditText): String? {
        return view.text?.toString()
    }

    override fun onFail(view: EditText, value: String?, message: String?) {
        view.error = message
    }

    override fun onPass(view: EditText, value: String?) {
        view.error = null
    }

    override fun reset(view: EditText) {
        view.error = null
    }
}