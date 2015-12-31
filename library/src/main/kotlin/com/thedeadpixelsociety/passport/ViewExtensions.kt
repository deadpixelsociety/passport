package com.thedeadpixelsociety.passport

import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText
import com.thedeadpixelsociety.passport.validators.EditTextViewValidator
import com.thedeadpixelsociety.passport.validators.TextInputLayoutViewValidator
import com.thedeadpixelsociety.passport.validators.ViewValidator

fun EditText.validator() = validator { EditTextViewValidator(it) }
fun TextInputLayout.validator() = validator { TextInputLayoutViewValidator(it) }

fun <V : View> V.validator(create: (V) -> ViewValidator<V>): ViewValidator<V> {
    var validator = validatorTag()
    if (validator == null) {
        validator = create(this)
        setTag(R.id.passport_validator_tag, validator)
    }

    return validator
}

@Suppress("UNCHECKED_CAST")
internal fun <V : View> V.validatorTag() = getTag(R.id.passport_validator_tag) as? ViewValidator<V>
