package com.kotlin.spring.coupon.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

private const val DEFAULT_LENGTH: Int = 10

class MemberTest {
    @Test
    fun 쿠폰을_발급한_이력이_없으면_false_를_리턴할_수_있다() {
        // given
        val member: Member = Member()

        // when
        val returned: Boolean = member.isCouponIssued()

        // then
        assertThat(returned).isEqualTo(false)
    }

    @Test
    fun 쿠폰을_발급한_이력이_있으면_true_를_리턴할_수_있다() {
        // given
        val member: Member = Member()

        // when
        member.issueCoupon()
        val returned: Boolean = member.isCouponIssued()

        // then
        assertThat(returned).isEqualTo(true)
    }

    @Test
    fun 이전에_발급한_쿠폰_값을_리턴할_수_있다() {
        // given
        val member: Member = Member()

        // when
        member.issueCoupon()
        val returned: Coupon? = member.getCoupon()
        val code: String = returned?.getCode() ?: ""

        // then
        assertThat(code.length).isEqualTo(DEFAULT_LENGTH)
    }
}
