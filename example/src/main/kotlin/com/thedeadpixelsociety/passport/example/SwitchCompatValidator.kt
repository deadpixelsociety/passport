package com.thedeadpixelsociety.passport.example

import android.support.v7.widget.SwitchCompat
import android.widget.TextView
import com.thedeadpixelsociety.passport.validators.SimpleViewValidator

class SwitchCompatValidator : SimpleViewValidator<SwitchCompat, Boolean>() {
    override fun getValue(view: SwitchCompat): Boolean {
        return view.isChecked
    }

    override fun onFail(view: SwitchCompat, value: Boolean, message: String?) {
        val errorView = view.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = message
    }

    override fun onPass(view: SwitchCompat, value: Boolean) {
        val errorView = view.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = null
    }

    override fun reset(view: SwitchCompat) {
        val errorView = view.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = null
    }
}
