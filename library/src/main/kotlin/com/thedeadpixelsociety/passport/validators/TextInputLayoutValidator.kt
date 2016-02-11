package com.thedeadpixelsociety.passport.validators

import android.support.design.widget.TextInputLayout

class TextInputLayoutValidator : SimpleViewValidator<TextInputLayout, String?>() {
    override fun getValue(view: TextInputLayout): String? {
        return view.editText?.text?.toString()
    }

    override fun onFail(view: TextInputLayout, value: String?, message: String?) {
        view.error = message
    }

    override fun onPass(view: TextInputLayout, value: String?) {
        view.error = null
    }

    override fun reset(view: TextInputLayout) {
        view.error = null
    }
}
