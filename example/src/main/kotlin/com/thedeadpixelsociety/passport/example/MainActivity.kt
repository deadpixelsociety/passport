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
import com.thedeadpixelsociety.passport.Functions
import com.thedeadpixelsociety.passport.Passport
import com.thedeadpixelsociety.passport.notEmpty
import com.thedeadpixelsociety.passport.passport

fun <T : View> Activity.findView(id: Int): T = findViewById(id)

class MainActivity : AppCompatActivity() {
    private val emailLayout by lazy { findView<TextInputLayout>(R.id.email_layout) }
    private val phoneEdit by lazy { findView<EditText>(R.id.phone_edit) }
    private val resetButton by lazy { findView<Button>(R.id.reset_button) }
    private val switchView by lazy { findView<SwitchCompat>(R.id._switch) }
    private val switchErrorView by lazy { findView<TextView>(R.id.switch_error) }
    private val textView by lazy { findView<TextView>(R.id.text_view) }
    private val validateButton by lazy { findView<Button>(R.id.validate_button) }

    private val validator by lazy {
        passport {
            rules<String?>(phoneEdit) {
                notEmpty()
                rule({ Functions.numeric(it) }, { getString(R.string.valid_phone_required) })
            }

            rules<Boolean>(switchView) {
                rule({ it }, { "The switch must be on." })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Passport.default(SwitchCompatValidator())

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
