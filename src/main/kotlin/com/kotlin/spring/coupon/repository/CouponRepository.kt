package com.kotlin.spring.coupon.repository

import com.kotlin.spring.coupon.model.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository : JpaRepository<Coupon, Long>
