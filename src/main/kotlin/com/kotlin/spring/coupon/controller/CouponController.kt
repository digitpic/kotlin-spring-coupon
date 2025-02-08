package com.kotlin.spring.coupon.controller

import com.kotlin.spring.coupon.controller.dto.CouponResponse
import com.kotlin.spring.coupon.model.Coupon
import com.kotlin.spring.coupon.service.CouponService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController(
    private val couponService: CouponService
) {
    @GetMapping("/issue/{userId}")
    fun issueCoupon(@PathVariable userId: Long): CouponResponse {
        val coupon: Coupon = couponService.issue(userId)
        val code: String = coupon.getCode()
        return CouponResponse("success", userId, code)
    }
}
