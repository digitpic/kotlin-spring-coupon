package com.kotlin.spring.coupon.model

import com.kotlin.spring.coupon.util.RandomUtils

class Coupon {
    companion object {
        private const val DEFAULT_LENGTH: Int = 10
        private const val UPPERCASE_REGEX: String = ".*[A-Z].*"
        private const val LOWERCASE_REGEX: String = ".*[a-z].*"
        private const val NUMERIC_REGEX: String = ".*[0-9].*"

        fun issue(): String {
            while (true) {
                val coupon: String = RandomUtils.generateString(DEFAULT_LENGTH)
                try {
                    validate(coupon)
                    return coupon
                } catch (e: RuntimeException) {
                    continue
                }
            }
        }

        private fun validate(coupon: String) {
            validateUpperCase(coupon)
            validateLowerCase(coupon)
            validateNumeric(coupon)
        }

        private fun validateUpperCase(coupon: String) {
            if (!coupon.contains(Regex(UPPERCASE_REGEX))) {
                throw RuntimeException()
            }
        }

        private fun validateLowerCase(coupon: String) {
            if (!coupon.contains(Regex(LOWERCASE_REGEX))) {
                throw RuntimeException()
            }
        }

        private fun validateNumeric(coupon: String) {
            if (!coupon.contains(Regex(NUMERIC_REGEX))) {
                throw RuntimeException()
            }
        }
    }
}
