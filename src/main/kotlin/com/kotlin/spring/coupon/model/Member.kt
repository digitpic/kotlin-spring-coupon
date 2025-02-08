package com.kotlin.spring.coupon.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
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

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "coupon_id", referencedColumnName = "id", unique = true)
    private var coupon: Coupon? = null
) {
    fun issueCoupon() {
        if (isIssued) {
            throw IllegalStateException("쿠폰을 발급 받은 이력이 있습니다.")
        }
        coupon = Coupon.issue()
        isIssued = true
    }

    fun isCouponIssued(): Boolean {
        return isIssued
    }

    fun getCoupon(): Coupon {
        return coupon!!
    }
}
