package com.kotlin.spring.coupon.model

class User(
    private var isIssued: Boolean = false,
    private var coupon: String = ""
) {
    fun issueCoupon() {
        coupon = Coupon.issue()
        isIssued = true
    }

    fun isCouponIssued(): Boolean {
        return isIssued
    }

    fun getCoupon(): String {
        if (coupon.isBlank()) {
            throw IllegalStateException("쿠폰을 발급한 이력이 없습니다.")
        }

        return coupon
    }
}
