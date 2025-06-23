package com.almumol.ossori.global.exception

data class BadRequestException(override val message: String) : RuntimeException()
