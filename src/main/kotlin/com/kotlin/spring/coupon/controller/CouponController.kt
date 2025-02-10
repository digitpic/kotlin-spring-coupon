package com.kotlin.spring.coupon.controller

import com.kotlin.spring.coupon.controller.dto.CouponResponse
import com.kotlin.spring.coupon.service.CouponService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController(
    private val couponService: CouponService
) {
    @GetMapping("/issue/{memberId}")
    fun issueCoupon(@PathVariable memberId: Long): ResponseEntity<CouponResponse> {
        val code: String? = couponService.issue(memberId)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok()
            .body(CouponResponse("success", memberId, code!!))
    }
}
