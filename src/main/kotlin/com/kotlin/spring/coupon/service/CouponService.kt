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
    fun issue(userId: Long): String? {
        while (true) {
            try {
                return transaction(userId)
            } catch (e: OptimisticLockingFailureException) {
                continue
            } catch (e: StaleObjectStateException) {
                continue
            }
        }
    }

    @Transactional
    fun transaction(userId: Long): String? {
        val member: Member = memberRepository.findById(userId)
            .orElseThrow { throw RuntimeException("회원 정보 없음") }
        if (member.isCouponIssued()) {
            return member.getCode()
        }

        val coupon: Coupon = couponRepository.findById(1L)
            .orElseThrow { RuntimeException("쿠폰 정보 없음") }
        if (coupon.getRemaining() == 0) {
            return null
        }

        var newCoupon: Coupon = Coupon.issue()
        while (memberRepository.findByCode(newCoupon.getCode()).isNotEmpty()) {
            newCoupon = Coupon.issue()
        }

        coupon.updateCode(newCoupon.getCode())
        coupon.decreaseRemaining()
        couponRepository.saveAndFlush(coupon)

        member.issueCoupon(coupon)
        memberRepository.save(member)

        return coupon.getCode()
    }
}
