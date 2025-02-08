package com.kotlin.spring.coupon.model

import com.kotlin.spring.coupon.util.RandomUtils
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "coupons")
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private var id: Long = 0,

    @Column(name = "code", unique = true)
    private val code: String
) {
    constructor() : this(0, "")

    companion object {
        private const val DEFAULT_LENGTH: Int = 10
        private const val UPPERCASE_REGEX: String = ".*[A-Z].*"
        private const val LOWERCASE_REGEX: String = ".*[a-z].*"
        private const val NUMERIC_REGEX: String = ".*[0-9].*"

        fun issue(): Coupon {
            while (true) {
                val code = RandomUtils.generateString(DEFAULT_LENGTH)
                try {
                    validate(code)
                    return Coupon(code = code)
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

    fun getCode(): String {
        return code
    }
}
