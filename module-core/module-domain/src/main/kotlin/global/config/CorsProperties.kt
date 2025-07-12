package com.almumol.ossori.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "config.cors")
data class CorsProperties (
    val mapping: String,
    val origins: List<String> = emptyList(),
    val methods: List<String> = listOf("*"),
)
