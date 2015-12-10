package com.thedeadpixelsociety.passport.example

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.thedeadpixelsociety.passport.*
import kotlin.text.Regex

@Suppress("UNCHECKED_CAST")
fun <T : View> Activity.findView(id: Int): T = findViewById(id) as T

class MainActivity() : AppCompatActivity() {
    val editText by lazy { findView<EditText>(android.R.id.edit) }
    val textInputLayout by lazy { findView<TextInputLayout>(android.R.id.inputArea) }
    val button by lazy { findView<Button>(android.R.id.button1) }
    val text by lazy { findView<TextView>(android.R.id.text1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            clearErrors()

            if (validateRules())
                text.text = "Validation passed!"
            else
                text.text = "Validation failed!"
        }
    }

    override fun onResume() {
        super.onResume()

        editText.addRule({ view, text -> text?.matches(Regex("[0-9]{3}-[0-9]{3}-[0-9]{4}")) ?: false },
                { "A valid phone number is required." })

        textInputLayout.addRule(email(), { "A valid email address is required." })
    }

    override fun onPause() {
        super.onPause()

        editText.clearRules()
        textInputLayout.clearRules()
    }
}
