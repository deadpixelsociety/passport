package com.thedeadpixelsociety.passport

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class PassportDelegate(func: Passport.() -> Passport) : ReadOnlyProperty<Any, Passport> {
    private val passport by lazy { Passport().func() }

    override fun getValue(thisRef: Any, property: KProperty<*>): Passport = passport
}