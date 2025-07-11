package com.almumol.ossori.global.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableConfigurationProperties(CorsProperties::class)
@Configuration
class CorsConfig(
    private val corsProperties: CorsProperties
) {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping(corsProperties.mapping)
                    .allowedOrigins(*corsProperties.origins.toTypedArray())
                    .allowedMethods(*corsProperties.methods.toTypedArray())
            }
        }
    }
}
