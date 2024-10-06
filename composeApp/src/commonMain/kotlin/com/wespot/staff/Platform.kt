package com.wespot.staff

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform