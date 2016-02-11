package com.thedeadpixelsociety.passport.example

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.thedeadpixelsociety.passport.Functions
import com.thedeadpixelsociety.passport.Rules
import com.thedeadpixelsociety.passport.passport
import com.thedeadpixelsociety.passport.validators.EditTextValidator
import com.thedeadpixelsociety.passport.validators.TextInputLayoutValidator

@Suppress("UNCHECKED_CAST")
fun <T : View> Activity.findView(id: Int): T = findViewById(id) as T

class MainActivity() : AppCompatActivity() {
    val emailLayout by lazy { findView<TextInputLayout>(R.id.email_layout) }
    val linearLayout by lazy { findView<LinearLayout>(R.id.passport_layout) }
    val phoneEdit by lazy { findView<EditText>(R.id.phone_edit) }
    val resetButton by lazy { findView<Button>(R.id.reset_button) }
    val switchView by lazy { findView<SwitchCompat>(R.id._switch) }
    val switchErrorView by lazy { findView<TextView>(R.id.switch_error) }
    val textView by lazy { findView<TextView>(R.id.text_view) }
    val validateButton by lazy { findView<Button>(R.id.validate_button) }

    private val validator by lazy {
        passport {
            with(phoneEdit, EditTextValidator()) {
                rule(Rules.required)
                rule({ Functions.numeric(it) }, { getString(R.string.valid_phone_required) })
            }

            with(emailLayout, TextInputLayoutValidator()) {
                rule(Rules.required)
                rule(Rules.emailRequired)
            }

            with(switchView, SwitchCompatValidator()) {
                rule({ it }, { "The switch must be on." })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        switchView.setTag(R.id.error_view_tag, switchErrorView)

        validateButton.setOnClickListener {
            if (validator.validate(this)) {
                textView.text = "Valid!"
            } else {
                textView.text = "Invalid!"
            }
        }

        resetButton.setOnClickListener { validator.reset(this) }
    }
}
