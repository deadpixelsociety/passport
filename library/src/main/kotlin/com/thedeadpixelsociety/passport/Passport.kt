package com.thedeadpixelsociety.passport

import android.app.Activity
import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import java.lang.ref.WeakReference
import java.util.*
import kotlin.text.Regex

fun notEmpty(): (View?, String?) -> Boolean = { view, text -> !text.isNullOrEmpty() }
fun notBlank(): (View?, String?) -> Boolean = { view, text -> !text.isNullOrBlank() }
fun length(min: Int = 0, max: Int = Int.MAX_VALUE): (View?, String?) -> Boolean = {
    view, text ->
    if (text != null) text.length >= min && text.length <= max else false
}

fun numeric(): (View?, String?) -> Boolean = { view, text -> text?.matches(Regex("[0-9]+")) ?: false }
fun alpha(): (View?, String?) -> Boolean = { view, text -> text?.matches(Regex("[a-zA-Z]+")) ?: false }
fun alphanumeric(): (View?, String?) -> Boolean = { view, text -> text?.matches(Regex("[a-zA-Z0-9]+")) ?: false }
fun lowercase(): (View?, String?) -> Boolean = { view, text -> text?.matches(Regex("[a-z]+")) ?: false }
fun uppercase(): (View?, String?) -> Boolean = { view, text -> text?.matches(Regex("[A-Z]+")) ?: false }
fun email(): (View?, String?) -> Boolean = { view, text -> text?.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) ?: false }

fun ViewGroup.validateRules(): Boolean {
    return walkViews(this) {
        if (it is EditText) it.validate()
        else if (it is TextInputLayout) it.validate()
        else true
    }
}

fun ViewGroup.clearErrors() {
    walkViews(this) {
        if (it is EditText) it.error = null
        else if (it is TextInputLayout) it.error = null

        true
    }
}

fun Activity.clearErrors() = clearErrors(getContentRoot())
fun Activity.clearErrors(root: View?) {
    walkViews(root) {
        if (it is EditText) it.error = null
        else if (it is TextInputLayout) it.error = null

        true
    }
}

fun Activity.validateRules(): Boolean = validateRules(getContentRoot())
fun Activity.validateRules(root: View?): Boolean {
    return walkViews(root, {
        val result = if (it is EditText) it.validate()
        else if (it is TextInputLayout) it.validate()
        else true

        result
    })
}

fun EditText.rules(): List<Rule> = rules<EditText>()
fun EditText.clearRules() = clearRules<EditText>()
fun EditText.addRule(func: (View?, String?) -> Boolean, message: () -> String?): EditText =
        addRule<EditText>(func, message)

fun EditText.validate(): Boolean = validate(wrap(), editableText.toString())

fun TextInputLayout.rules(): List<Rule> = rules<TextInputLayout>()
fun TextInputLayout.clearRules() = clearRules<TextInputLayout>()
fun TextInputLayout.addRule(func: (View?, String?) -> Boolean, message: () -> String?): TextInputLayout =
        addRule<TextInputLayout>(func, message)

fun TextInputLayout.validate(): Boolean = validate(wrap(), editText.editableText.toString())

class Rule(val func: (View?, String?) -> Boolean, val message: () -> String?) {
    internal fun <V : View> test(view: ErrorView<V>, text: String?) = if (func(view.viewRef.get(), text)) true else fail(view)

    internal fun <V : View> fail(view: ErrorView<V>): Boolean {
        view.error = message()
        return false
    }
}

internal fun <T : View> T.rules(): List<Rule> = RuleStore[id]

internal fun <T : View> T.clearRules() = RuleStore.clear(id)

internal fun <T : View> T.addRule(func: (View?, String?) -> Boolean, message: () -> String?): T {
    RuleStore[id].add(Rule(func, message))
    return this
}

internal fun <T : View> T.validate(errorView: ErrorView<T>, text: String?): Boolean {
    val valid = rules().firstOrNull { !it.test(errorView, text) } == null
    if (valid) errorView.error = null

    return valid
}

internal interface ErrorView<V : View> {
    val viewRef: WeakReference<V>
    var error: String?
}

internal class EditTextErrorView(view: EditText) : ErrorView<EditText> {
    override val viewRef = WeakReference(view)
    override var error: String? = null
        set(value) {
            viewRef.get()?.error = value
        }
}

internal class TextInputLayoutErrorView(view: TextInputLayout) : ErrorView<TextInputLayout> {
    override val viewRef = WeakReference(view)
    override var error: String? = null
        set(value) {
            viewRef.get()?.error = value
        }
}

internal object RuleStore {
    private val rules = HashMap<Int, MutableList<Rule>>()

    operator fun get(id: Int) = ruleList(id)

    fun clear(id: Int) = rules.remove(id)

    private fun ruleList(id: Int): MutableList<Rule> {
        var list = rules[id]
        if (list == null) {
            list = arrayListOf()
            rules[id] = list
        }

        return list
    }
}

internal fun EditText.wrap() = EditTextErrorView(this)

internal fun TextInputLayout.wrap() = TextInputLayoutErrorView(this)

internal fun Activity.getContentRoot() = (findViewById(android.R.id.content) as ViewGroup).getChildAt(0)

internal fun walkViews(root: View?, func: (View) -> Boolean): Boolean {
    var valid = true

    if (root != null) {
        if (root is ViewGroup) {
            if (root is TextInputLayout) {
                valid = func(root)
            } else {
                val results = arrayListOf<Boolean>()

                for (i in 0..root.childCount)
                    results.add(walkViews(root.getChildAt(i), func))

                valid = results.all { it }
            }
        } else if (root is EditText) {
            valid = func(root)
        }
    }

    return valid
}
