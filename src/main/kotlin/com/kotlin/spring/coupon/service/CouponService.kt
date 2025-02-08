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
    fun issue(userId: Long): Coupon? {
        couponRepository.lockCouponsTable()

        val member: Member = memberRepository.findById(userId)
            .orElseThrow({ throw RuntimeException("회원 정보 없음") })

        if (checkIssued(member)) {
            return member.getCoupon()
        }

        val couponCount: Long = couponRepository.countCoupons()

        if (couponCount >= MAX_COUPON_COUNT) {
            return null
        }

        member.issueCoupon()

        val coupon: Coupon = member.getCoupon()

        couponRepository.save(coupon)

        return coupon
    }

    private fun checkIssued(member: Member): Boolean {
        return member.isCouponIssued()
    }
}
