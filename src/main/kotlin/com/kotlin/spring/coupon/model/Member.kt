package com.kotlin.spring.coupon.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "members")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private val id: Long = 0,

    @Column(name = "is_issued")
    private var isIssued: Boolean = false,

    @Column(name = "code")
    private var code: String
) {
    constructor() : this(code = "")

    fun issueCoupon(coupon: Coupon) {
        if (isIssued) {
            throw IllegalStateException("쿠폰을 발급 받은 이력이 있습니다.")
        }
        this.code = coupon.getCode()
        isIssued = true
    }

    fun isCouponIssued(): Boolean {
        return isIssued
    }

    fun getCode(): String {
        return code
    }
}
