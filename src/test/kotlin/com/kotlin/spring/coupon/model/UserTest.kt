package com.kotlin.spring.coupon.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private const val DEFAULT_LENGTH: Int = 10

class UserTest {
    @Test
    fun 쿠폰을_발급한_이력이_없으면_false_를_리턴할_수_있다() {
        // given
        val user: User = User()

        // when
        val returned: Boolean = user.isCouponIssued()

        // then
        assertThat(returned).isEqualTo(false)
    }

    @Test
    fun 쿠폰을_발급한_이력이_있으면_true_를_리턴할_수_있다() {
        // given
        val user: User = User()

        // when
        user.issueCoupon()
        val returned: Boolean = user.isCouponIssued()

        // then
        assertThat(returned).isEqualTo(true)
    }

    @Test
    fun 이전에_발급한_쿠폰_값을_리턴할_수_있다() {
        // given
        val user: User = User()

        // when
        user.issueCoupon()
        val returned: String = user.getCoupon()

        // then
        assertThat(returned.length).isEqualTo(DEFAULT_LENGTH)
    }
}
