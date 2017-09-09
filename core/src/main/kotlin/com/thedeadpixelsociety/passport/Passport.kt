package com.thedeadpixelsociety.passport

import android.app.Activity
import android.app.Fragment
import android.view.View
import android.widget.EditText
import java.lang.IllegalArgumentException

/**
 * Core control class used to create rule sets, reset views and run validation logic.
 */
class Passport {
    companion object {
        val DEFAULT_VALIDATORS = hashMapOf<Class<*>, ValidatorFactory<*, *>>()

        init {
            validatorFactory({ TextViewValidator() })
            validatorFactory(EditText::class.java, { TextViewValidator() })
        }

        /**
         * Sets the validatorFactory validator for a given target class.
         * @param targetClass The target class.
         * @param factory The validatorFactory validator factory to use.
         */
        fun <V, T> validatorFactory(targetClass: Class<V>, factory: ValidatorFactory<V, T>) {
            DEFAULT_VALIDATORS[targetClass] = factory
        }

        /**
         * Sets the validatorFactory validator for a given target class.
         * @param factory The validatorFactory validator factory to use.
         */
        inline fun <reified V : Any, T> validatorFactory(noinline factory: ValidatorFactory<V, T>) {
            DEFAULT_VALIDATORS[V::class.java] = factory
        }

        private tailrec fun <V, T> findDefault(targetClass: Class<*>): Validator<V, T>? {
            @Suppress("UNCHECKED_CAST")
            val factory = DEFAULT_VALIDATORS[targetClass] as? ValidatorFactory<V, T>
            if (factory != null) return factory()
            val superClass = targetClass.superclass ?: return null
            return findDefault(superClass)
        }
    }

    /**
     * Creates a rule set for the given view. The validatorFactory registered view validator will be used.
     * @param view The view to create rules for.
     * @see validatorFactory
     */
    fun <T> rules(view: View, func: RuleCollection<T>.() -> Unit): Passport = rules(view, view.javaClass, func)

    /**
     * Creates a rule set for the given view. The validatorFactory registered view validator will be used.
     * @param view The view to create rules for.
     * @param viewClass The view class that registered the validatorFactory validator.
     * @see validatorFactory
     */
    fun <V : View, T> rules(
            view: V,
            viewClass: Class<V>,
            func: RuleCollection<T>.() -> Unit
    ): Passport {
        @Suppress("UNCHECKED_CAST")
        val validator = findDefault<V, T>(viewClass)
                ?: throw IllegalArgumentException("No validatorFactory validator found for view class ${viewClass.simpleName}")
        validator.func()
        view.setTag(R.id.passport_validator_tag, validator)
        return this
    }

    /**
     * Creates a rule set for the given view.
     * @param view The view to create rules for.
     * @param validator The [Validator] to use.
     */
    fun <V : View, T> rules(
            view: V,
            validator: Validator<V, T>,
            func: RuleCollection<T>.() -> Unit
    ): Passport {
        validator.func()
        view.setTag(R.id.passport_validator_tag, validator)
        return this
    }

    /**
     * Resets validation for the target view and all child views.
     * @param view The view to reset.
     */
    fun reset(view: View) {
        walkViews(view) { it.validatorTag()?.reset(it) }
    }

    /**
     * Resets validation for the target fragment's view.
     * @param fragment The fragment to reset.
     */
    fun reset(fragment: Fragment) = fragment.view?.let { reset(it) }

    /**
     * Resets validation for the target activity's view.
     * @param activity The activity to reset.
     */
    fun reset(activity: Activity) {
        rootView(activity)?.let { reset(it) }
    }

    /**
     * Validates the specified view and all child views.
     * @param view The view to validate.
     * @param method The [ValidationMethod] to use. Defaults to [ValidationMethod.BATCH].
     * @return true if validation passed; otherwise, false.
     */
    fun validate(view: View, method: ValidationMethod = ValidationMethod.BATCH): Boolean {
        fun validateView(target: View) = target.validatorTag()?.validate(target, method) ?: true
        return allViews(view, method == ValidationMethod.IMMEDIATE) { validateView(it) }
    }

    /**
     * Validates the specified fragment and all child views.
     * @param fragment The fragment to validate.
     * @param method The [ValidationMethod] to use. Defaults to [ValidationMethod.BATCH].
     * @return true if validation passed; otherwise, false.
     */
    fun validate(
            fragment: Fragment,
            method: ValidationMethod = ValidationMethod.BATCH
    ): Boolean = fragment.view?.let { validate(it, method) } ?: true

    /**
     * Validates the specified activity and all child views.
     * @param activity The activity to validate.
     * @param method The [ValidationMethod] to use. Defaults to [ValidationMethod.BATCH].
     * @return true if validation passed; otherwise, false.
     */
    fun validate(
            activity: Activity,
            method: ValidationMethod = ValidationMethod.BATCH
    ): Boolean = rootView(activity)?.let { validate(it, method) } ?: true
}
