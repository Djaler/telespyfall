package com.github.djaler.telespyfall

import com.github.djaler.telespyfall.config.TelegramProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
