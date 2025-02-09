package com.kotlin.spring.coupon.repository

import com.kotlin.spring.coupon.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByCode(code: String): MutableList<Member>
}
