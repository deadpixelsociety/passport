package com.thedeadpixelsociety.passport.example

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.thedeadpixelsociety.passport.*

@Suppress("UNCHECKED_CAST")
fun <T : View> Activity.findView(id: Int): T = findViewById(id) as T

class MainActivity() : AppCompatActivity() {
    val editText by lazy { findView<EditText>(android.R.id.edit) }
    val button by lazy { findView<Button>(android.R.id.button1) }
    val linearLayout by lazy { findView<LinearLayout>(R.id.passport_layout) }
    val text by lazy { findView<TextView>(android.R.id.text1) }
    val textInputLayout by lazy { findView<TextInputLayout>(android.R.id.inputArea) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Add a single rule to the EditText.
        editText.validator().add(Passport.Rules.phoneRequired)

        // Rules can easily be chained as both pre-defined and custom variants.
        textInputLayout.validator()
                .add(Passport.Rules.emailRequired)
                .add({ view, string -> false }, { "This rule always fails." })

        // Rules can be pre-defined and used in many places.
        val rule = Rule({ view, string -> true }, { "This rule always passes." })
        // The validator() function will always return the same ViewValidator instance for the lifetime of the view.
        editText.validator().add(rule)

        button.setOnClickListener {
            // Clear all current errors in the activity's view tree.
            clearErrors()

            // Can also clear individual View errors.
            // editText.validator().error = null

            // Or focus on a particular ViewGroup.
            // linearLayout.clearErrors()

            // Apply all current validation rules in the activity's view tree.
            if (validateRules())
                text.text = "Validation passed!"
            else
                text.text = "Validation failed!"

            /* Validate an individual view.
            if (editText.validator().validate())
                text.text = "Validation passed!"
            else
                text.text = "Validation failed!"
            */

            /* Validate a particular ViewGroup
            if (linearLayout.validateRules())
                text.text = "Validation passed!"
            else
                text.text = "Validation failed!"
            */
        }
    }

    override fun onDestroy() {
        // Clean up all references. Not necessarily required, but if you like being neat.
        destroyValidators()

        // editText.validator().destroy()
        //linearLayout.destroyValidators()

        super.onDestroy()
    }
}
