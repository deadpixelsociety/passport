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
     * The fail fast validation method will process all validators until a failure is encountered
     * and then proceed onto the next validator immediately.
     */
    FAIL_FAST,
    /**
     * The immediate validation method will process all validators until a failure is encountered
     * and then return immediately.
     */
    IMMEDIATE
}