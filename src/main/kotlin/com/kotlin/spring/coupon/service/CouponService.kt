package com.kotlin.spring.coupon.service

import com.kotlin.spring.coupon.model.Coupon
import com.kotlin.spring.coupon.model.Member
import com.kotlin.spring.coupon.repository.CouponRepository
import com.kotlin.spring.coupon.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

private const val MAX_COUPON_COUNT = 100

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun issue(memberId: Long): Coupon? {
        couponRepository.lockCouponsTable()

        val member: Member = memberRepository.findById(memberId)
            .orElseThrow({ throw RuntimeException("회원 정보 없음") })
        if (member.isCouponIssued()) {
            return member.getCoupon()
        }

        if (isOverCouponLimit()) {
            return null
        }

        val coupon: Coupon = issueUniqueCoupon()

        member.issueCoupon(coupon)
        couponRepository.save(coupon)

        return coupon
    }

    private fun issueUniqueCoupon(): Coupon {
        var coupon: Coupon = Coupon.issue()
        while (couponRepository.findByCode(coupon.getCode()).isNotEmpty()) {
            coupon = Coupon.issue()
        }
        return coupon
    }

    private fun isOverCouponLimit(): Boolean {
        return couponRepository.count() >= MAX_COUPON_COUNT
    }
}
