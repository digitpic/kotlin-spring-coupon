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
        val returned: Boolean = member.isIssuedCoupon()

        // then
        assertThat(returned).isEqualTo(false)
    }

    @Test
    fun 쿠폰을_발급한_이력이_있으면_true_를_리턴할_수_있다() {
        // given
        val member: Member = Member()

        // when
        val coupon: Coupon = Coupon.issue()
        member.issueCoupon(coupon)
        val returned: Boolean = member.isIssuedCoupon()

        // then
        assertThat(returned).isEqualTo(true)
    }

    @Test
    fun 이전에_발급한_쿠폰_값을_리턴할_수_있다() {
        // given
        val member: Member = Member()

        // when
        val coupon: Coupon = Coupon.issue()
        member.issueCoupon(coupon)
        val returned: String? = member.getCode()

        // then
        assertThat(returned!!.length).isEqualTo(DEFAULT_LENGTH)
    }
}
