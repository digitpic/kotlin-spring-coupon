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
    fun issue(userId: Long): Coupon {
        val member: Member = memberRepository.findById(userId).get()

        if (checkIssued(member)) {
            return member.getCoupon()
        }

        if (couponCount() >= MAX_COUPON_COUNT) {
            return Coupon(0, "")
        }

        member.issueCoupon()

        val coupon: Coupon = member.getCoupon()

        couponRepository.save(coupon)

        return coupon
    }

    private fun couponCount(): Long {
        return couponRepository.findAll()
            .stream()
            .count()
    }

    private fun checkIssued(member: Member): Boolean {
        return member.isCouponIssued()
    }
}
