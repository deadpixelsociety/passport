package com.thedeadpixelsociety.passport

import android.view.View

class EmptyValidator : BaseValidator<View, Unit>() {
    override fun value(target: View) {
    }
}