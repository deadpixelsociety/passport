package com.thedeadpixelsociety.passport

/**
 * The validation method determines how validators are processed.
 */
enum class ValidationMethod {
    /**
     * The batch validation method will process all validators before returning a result.
     */
    BATCH,
    /**
     * The immediate validation method will process all validators until a failure is encountered
     * and then return immediately.
     */
    IMMEDIATE
}