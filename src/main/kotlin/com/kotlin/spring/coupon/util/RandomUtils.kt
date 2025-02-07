package com.kotlin.spring.coupon.util

import org.apache.commons.lang3.RandomStringUtils

class RandomUtils {
    companion object {
        fun generateString(length: Int): String {
            return RandomStringUtils.secure()
                .nextAlphanumeric(length)
        }
    }
}
