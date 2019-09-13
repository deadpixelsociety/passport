package com.thedeadpixelsociety.passport.example

import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.thedeadpixelsociety.passport.BaseValidator

class SwitchMaterialValidator : BaseValidator<SwitchMaterial, Boolean>() {
    override fun value(target: SwitchMaterial) = target.isChecked

    override fun passed(target: SwitchMaterial, value: Boolean) {
        val errorView = target.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = null
    }

    override fun failed(target: SwitchMaterial, value: Boolean, messages: List<String>) {
        val errorView = target.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = messages.joinToString(" ")
    }

    override fun reset(target: SwitchMaterial) {
        val errorView = target.getTag(R.id.error_view_tag) as? TextView
        errorView?.text = null
    }
}
