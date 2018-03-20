package com.thedeadpixelsociety.passport.example

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.thedeadpixelsociety.passport.*

fun <T : View> Activity.findView(id: Int): T = findViewById(id)

class MainActivity : AppCompatActivity() {
    private val emailLayout by lazy { findView<TextInputLayout>(R.id.email_layout) }
    private val phoneEdit by lazy { findView<EditText>(R.id.phone_edit) }
    private val resetButton by lazy { findView<Button>(R.id.reset_button) }
    private val switchView by lazy { findView<SwitchCompat>(R.id._switch) }
    private val switchErrorView by lazy { findView<TextView>(R.id.switch_error) }
    private val textView by lazy { findView<TextView>(R.id.text_view) }
    private val batchValidateButton by lazy { findView<Button>(R.id.batch_validate_button) }
    private val failFastValidateButton by lazy { findView<Button>(R.id.fail_fast_validate_button) }
    private val immediateValidateButton by lazy { findView<Button>(R.id.immediate_validate_button) }

    private val validator by validator {
        rules<String>(phoneEdit) {
            numeric(getString(R.string.valid_phone_required))
        }

        rules<String>(emailLayout) {
            email()
            length(8, 32)
        }

        rules<Boolean>(switchView) {
            rule({ it }, { "The switch must be on." })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set custom validators.
        Passport.validatorFactory({ SwitchCompatValidator() })
        Passport.validatorFactory({ TextInputLayoutValidator() })

        switchView.setTag(R.id.error_view_tag, switchErrorView)

        fun validate(method: ValidationMethod) {
            if (validator.validate(this, method)) {
                textView.text = getString(R.string.valid)
            } else {
                textView.text = getString(R.string.invalid)
            }
        }

        batchValidateButton.setOnClickListener { validate(ValidationMethod.BATCH) }
        failFastValidateButton.setOnClickListener { validate(ValidationMethod.FAIL_FAST) }
        immediateValidateButton.setOnClickListener { validate(ValidationMethod.IMMEDIATE) }

        resetButton.setOnClickListener {
            validator.reset(this)
            textView.text = getString(R.string.waiting)
        }
    }
}
