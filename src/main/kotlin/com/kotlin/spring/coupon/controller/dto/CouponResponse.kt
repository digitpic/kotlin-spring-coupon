package com.kotlin.spring.coupon.controller.dto

data class CouponResponse(
    val message: String,
    val userId: Long,
    val code: String
)
