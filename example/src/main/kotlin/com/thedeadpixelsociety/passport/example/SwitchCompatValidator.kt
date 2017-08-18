package com.thedeadpixelsociety.passport.example

import android.support.v7.widget.SwitchCompat
import android.widget.TextView
import com.thedeadpixelsociety.passport.BaseValidator

class SwitchCompatValidator : BaseValidator<SwitchCompat, Boolean>() {
    override fun value(target: SwitchCompat) = target.isChecked

    override fun passed(target: SwitchCompat, value: Boolean) {
        val errorView = target.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = null
    }

    override fun failed(target: SwitchCompat, value: Boolean, messages: List<String>) {
        val errorView = target.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = messages.joinToString(" ")
    }

    override fun reset(view: SwitchCompat) {
        val errorView = view.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = null
    }
}
