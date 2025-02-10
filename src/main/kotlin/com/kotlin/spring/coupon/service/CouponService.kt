package com.kotlin.spring.coupon.service

import com.kotlin.spring.coupon.model.Coupon
import com.kotlin.spring.coupon.model.Member
import com.kotlin.spring.coupon.repository.CouponRepository
import com.kotlin.spring.coupon.repository.MemberRepository
import jakarta.transaction.Transactional
import org.hibernate.StaleObjectStateException
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val memberRepository: MemberRepository
) {
    fun issue(memberId: Long): String? {
        while (true) {
            try {
                return transaction(memberId)
            } catch (e: OptimisticLockingFailureException) {
                continue
            } catch (e: StaleObjectStateException) {
                continue
            }
        }
    }

    @Transactional
    fun transaction(memberId: Long): String? {
        val member: Member = memberRepository.findById(memberId)
            .orElseThrow { throw RuntimeException("회원 정보 없음") }
        if (member.isIssuedCoupon()) {
            return member.getCode()
        }

        val coupon: Coupon = couponRepository.findById(1L)
            .orElseThrow { RuntimeException("쿠폰 정보 없음") }
        if (isOverCouponLimit(coupon)) {
            return null
        }

        val newCoupon: Coupon = issueUniqueCoupon()

        coupon.updateCode(newCoupon.getCode())
        coupon.decreaseRemaining()
        couponRepository.saveAndFlush(coupon)

        member.issueCoupon(coupon)
        memberRepository.save(member)

        return coupon.getCode()
    }

    private fun isOverCouponLimit(coupon: Coupon) = coupon.getRemaining() == 0

    private fun issueUniqueCoupon(): Coupon {
        var newCoupon: Coupon = Coupon.issue()
        while (memberRepository.findByCode(newCoupon.getCode()).isNotEmpty()) {
            newCoupon = Coupon.issue()
        }
        return newCoupon
    }
}
