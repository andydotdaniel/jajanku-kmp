package com.andydotdaniel.jajanku

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform