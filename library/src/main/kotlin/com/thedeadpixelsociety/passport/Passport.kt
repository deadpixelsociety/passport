package com.thedeadpixelsociety.passport

import android.app.Activity
import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import java.lang.ref.WeakReference
import java.util.*
import kotlin.text.Regex

object Passport {
    object Rules {
        val emailRequired = Rule(email(), { "A valid email address is required." })
        // source: http://stackoverflow.com/a/123666
        val phoneRequired = Rule(matches("^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$"),
                { "A valid phone number is required." })
        val numbersOnly = Rule(numeric(), { "Only numbers may be used." })
        val lettersOnly = Rule(alpha(), { "Only letters may be used." })
        val alphanumericOnly = Rule(alpha(), { "Only letters and numbers may be used." })
    }

    fun matches(pattern: String) = matches(Regex(pattern))
    fun matches(regex: Regex): (View?, String?) -> Boolean = { view, text -> text?.matches(regex) ?: false }
    fun notMatches(pattern: String) = notMatches(Regex(pattern))
    fun notMatches(regex: Regex): (View?, String?) -> Boolean = { view, text -> !(text?.matches(regex) ?: false) }

    fun notEmpty(): (View?, String?) -> Boolean = { view, text -> !text.isNullOrEmpty() }
    fun notBlank(): (View?, String?) -> Boolean = { view, text -> !text.isNullOrBlank() }

    fun length(min: Int = 0, max: Int = Int.MAX_VALUE): (View?, String?) -> Boolean = {
        view, text ->
        if (text != null) text.length >= min && text.length <= max else false
    }

    fun numeric() = matches("[0-9]*")
    fun alpha() = matches("[a-zA-Z]*")
    fun alphanumeric() = matches("[a-zA-Z0-9]*")
    fun email() = matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
}

class Rule(val func: (View?, String?) -> Boolean, val message: () -> String?) {
    operator fun <V : View> invoke(validator: ViewValidator<V>): Boolean {
        if (func(validator.viewRef.get(), validator.text)) return true

        validator.error = message()
        return false
    }
}

class RuleList() : ArrayList<Rule>() {
    fun add(func: (View?, String?) -> Boolean, message: () -> String?): RuleList {
        add(Rule(func, message))
        return this
    }

    fun <T : View> apply(validator: ViewValidator<T>): Boolean {
        val valid = firstOrNull { !it(validator) } == null
        if (valid) validator.error = null

        return valid
    }
}

interface ViewValidator<V : View> {
    var error: String?
    var text: String?
    val viewRef: WeakReference<V>

    fun rules(): RuleList {
        val view = viewRef.get()
        if (view != null)
            return RuleStore[view.id]

        throw IllegalStateException("View reference has been lost.")
    }

    fun add(rule: Rule) {
        rules().add(rule)
    }

    fun add(func: (View?, String?) -> Boolean, message: () -> String?) {
        rules().add(Rule(func, message))
    }

    fun clear() {
        val view = viewRef.get()
        if (view != null) {
            RuleStore.clear(view.id)
            ValidatorStore.clear(view.id)
        }
    }

    fun validate(): Boolean {
        return rules().apply(this)
    }
}

abstract class ViewValidatorWrapper<V : View>(view: V) : ViewValidator<V> {
    override val viewRef = WeakReference(view)

    init {
        ValidatorStore[view.id] = this
    }
}

class EditTextViewValidatorWrapper(view: EditText) : ViewValidatorWrapper<EditText>(view) {
    override var error: String?
        get() = viewRef.get()?.error?.toString()
        set(value) {
            viewRef.get()?.error = value
        }
    override var text: String?
        get() = viewRef.get()?.text?.toString()
        set(value) {
            viewRef.get()?.setText(value)
        }
}

class TextInputLayoutViewValidatorWrapper(view: TextInputLayout) : ViewValidatorWrapper<TextInputLayout>(view) {
    override var error: String?
        get() = viewRef.get()?.error?.toString()
        set(value) {
            viewRef.get()?.error = value
        }
    override var text: String?
        get() = viewRef.get()?.editText?.text?.toString()
        set(value) {
            viewRef.get()?.editText?.setText(value)
        }
}

fun EditText.validator() = EditTextViewValidatorWrapper(this)
fun TextInputLayout.validator() = TextInputLayoutViewValidatorWrapper(this)

fun Activity.clearErrors() = clearErrors(getContentRoot())
fun Activity.clearErrors(root: View?) {
    walkViews(root) {
        ValidatorStore[it.id]?.error = null
        true
    }
}

fun ViewGroup.clearErrors() {
    walkViews(this) {
        ValidatorStore[it.id]?.error = null
        true
    }
}

fun Activity.clearRules() = clearRules(getContentRoot())
fun Activity.clearRules(root: View?) {
    walkViews(root) {
        ValidatorStore[it.id]?.clear()
        true
    }
}

fun ViewGroup.clearRules() {
    walkViews(this) {
        ValidatorStore[it.id]?.clear()
        true
    }
}

fun Activity.validateRules(): Boolean = validateRules(getContentRoot())
fun Activity.validateRules(root: View?): Boolean {
    return walkViews(root, {
        val validator = ValidatorStore[it.id]
        validator == null || validator.validate()
    })
}

fun ViewGroup.validateRules(): Boolean {
    return walkViews(this) {
        val validator = ValidatorStore[it.id]
        validator == null || validator.validate()
    }
}

internal object ValidatorStore {
    private val validators = HashMap<Int, ViewValidator<*>>()

    operator fun get(id: Int) = validators[id]

    operator fun set(id: Int, validator: ViewValidator<*>) {
        validators[id] = validator
    }

    fun clear(id: Int) {
        validators.remove(id)
    }
}

internal object RuleStore {
    private val rules = HashMap<Int, RuleList>()

    operator fun get(id: Int) = ruleList(id)

    fun clear(id: Int) = rules.remove(id)

    private fun ruleList(id: Int): RuleList {
        var list = rules[id]
        if (list == null) {
            list = RuleList()
            rules[id] = list
        }

        return list
    }
}

internal fun Activity.getContentRoot() = (findViewById(android.R.id.content) as ViewGroup).getChildAt(0)

internal fun walkViews(root: View?, func: (View) -> Boolean): Boolean {
    var valid = true

    if (root != null) {
        val validator = ValidatorStore[root.id]
        if (validator != null) {
            valid = func(root)
        } else if (root is ViewGroup) {
            val results = arrayListOf<Boolean>()

            for (i in 0..root.childCount)
                results.add(walkViews(root.getChildAt(i), func))

            valid = results.all { it }
        }
    }

    return valid
}
