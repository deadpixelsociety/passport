package com.thedeadpixelsociety.passport.validators

import android.view.View
import com.thedeadpixelsociety.passport.R
import com.thedeadpixelsociety.passport.Rule
import java.lang.ref.WeakReference

abstract class ViewValidator<V : View>(view: V) {
    abstract var error: String?
    abstract var text: String?
    private val rules = arrayListOf<Rule>()
    private val viewRef = WeakReference(view)

    init {
        view.setTag(R.id.passport_validator_tag, this)
    }

    fun add(rule: Rule): ViewValidator<V> {
        rules.add(rule)
        return this
    }

    fun add(func: (View?, String?) -> Boolean, message: () -> String?): ViewValidator<V> {
        rules.add(Rule(func, message))
        return this
    }

    fun clear() {
        rules.clear()
    }

    fun destroy() {
        clear()

        viewRef.get()?.setTag(R.id.passport_validator_tag, null)
        viewRef.clear()
    }

    fun rules(): List<Rule> = rules

    fun validate(): Boolean {
        val invalidRule = rules.firstOrNull { !it(this) }
        if (invalidRule != null) {
            error = invalidRule.message()
            return false
        } else {
            error = null
            return false
        }
    }

    fun view(): V? = viewRef.get()
}