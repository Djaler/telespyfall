package com.github.djaler.telespyfall.config

import io.sentry.SentryClient
import io.sentry.SentryClientFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SentryConfig {
    @Bean
    fun sentryClient(): SentryClient {
        return SentryClientFactory.sentryClient()
    }
}
