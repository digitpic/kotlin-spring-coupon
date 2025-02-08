package com.kotlin.spring.coupon.repository

import com.kotlin.spring.coupon.model.Coupon
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository : JpaRepository<Coupon, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c")
    fun lockCouponsTable(): List<Coupon>

    @Query("SELECT COUNT(c) FROM Coupon c")
    fun countCoupons(): Long
}
