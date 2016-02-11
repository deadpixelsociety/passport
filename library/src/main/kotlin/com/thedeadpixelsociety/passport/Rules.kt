package com.thedeadpixelsociety.passport

object Rules {
    val emailRequired: Rule<String?> = Rule({ Functions.notBlank(it) && Functions.email(it) }, { "A valid email address is required." })
    val required: Rule<String?> = Rule({ Functions.notBlank(it) }, { "A value is required." })
}