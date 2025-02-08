package com.kotlin.spring.coupon.service

import com.kotlin.spring.coupon.model.Coupon
import com.kotlin.spring.coupon.model.Member
import com.kotlin.spring.coupon.repository.CouponRepository
import com.kotlin.spring.coupon.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun issue(userId: Long): Coupon {
        val member: Member = memberRepository.findById(userId).get()

        if (member.isCouponIssued()) {
            return member.getCoupon()
        }

        member.issueCoupon()

        val coupon: Coupon = member.getCoupon()

        couponRepository.save(coupon)

        return coupon
    }
}
