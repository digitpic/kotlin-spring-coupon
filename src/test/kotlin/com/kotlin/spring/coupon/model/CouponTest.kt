package com.kotlin.spring.coupon.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private const val DEFAULT_LENGTH: Int = 10
private const val UPPERCASE_REGEX: String = ".*[A-Z].*"
private const val LOWERCASE_REGEX: String = ".*[a-z].*"
private const val NUMERIC_REGEX: String = ".*[0-9].*"

class CouponTest {
    @Test
    fun 발급한_쿠폰은_대문자를_포함한다() {
        // when & then
        val code: String = Coupon.issue()
            .getCode()

        // then
        assertThat(code).matches(UPPERCASE_REGEX)
    }

    @Test
    fun 발급한_쿠폰은_소문자를_포함한다() {
        // when
        val code: String = Coupon.issue()
            .getCode()

        // then
        assertThat(code).matches(LOWERCASE_REGEX)
    }

    @Test
    fun 발급한_쿠폰은_숫자를_포함한다() {
        // when
        val code: String = Coupon.issue()
            .getCode()

        // then
        assertThat(code).matches(NUMERIC_REGEX)
    }

    @Test
    fun 발급한_쿠폰의_길이는_10_이다() {
        // when
        val code: String = Coupon.issue()
            .getCode()

        // then
        assertThat(code.length).isEqualTo(DEFAULT_LENGTH)
    }
}
