package com.github.abraga.fuzetest.utils

val Int?.orZero: Int
    get() {
        return this ?: 0
    }