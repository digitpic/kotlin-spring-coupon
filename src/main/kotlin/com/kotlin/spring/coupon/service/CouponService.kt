package com.kotlin.spring.coupon.service

import com.kotlin.spring.coupon.model.Coupon
import com.kotlin.spring.coupon.model.Member
import com.kotlin.spring.coupon.repository.CouponRepository
import com.kotlin.spring.coupon.repository.MemberRepository
import java.util.concurrent.TimeUnit
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val MAX_COUPON_COUNT = 100

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val memberRepository: MemberRepository,
    private val redissonClient: RedissonClient
) {
    @Cacheable(value = ["coupons"], key = "#memberId")
    @Transactional
    fun issue(memberId: Long): Coupon? {
        val lock: RLock = redissonClient.getLock("coupon-lock")
        if (!lock.tryLock(3, 3, TimeUnit.SECONDS)) {
            throw RuntimeException("lock 실패")
        }

        val member: Member = memberRepository.findById(memberId)
            .orElseThrow { throw RuntimeException("회원 정보 없음") }

        if (member.isIssuedCoupon()) {
            return member.getCoupon()
        }

        if (isOverCouponLimit()) {
            return null
        }

        val coupon: Coupon = issueUniqueCoupon()

        member.issueCoupon(coupon)
        couponRepository.save(coupon)

        lock.unlock()
        return coupon
    }

    private fun isOverCouponLimit() = couponRepository.count() >= MAX_COUPON_COUNT

    private fun issueUniqueCoupon(): Coupon {
        var coupon: Coupon = Coupon.issue()
        while (couponRepository.findByCode(coupon.getCode()).isNotEmpty()) {
            coupon = Coupon.issue()
        }
        return coupon
    }
}
