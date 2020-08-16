package com.github.djaler.telespyfall.handlers.commands

import com.github.djaler.telespyfall.handlers.CommandHandler
import com.github.djaler.telespyfall.service.LocationService
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.extensions.api.send.sendMessage
import com.github.insanusmokrassar.TelegramBotAPI.types.ExtendedBot
import com.github.insanusmokrassar.TelegramBotAPI.types.message.CommonMessageImpl
import org.springframework.stereotype.Component

@Component
class GetLocationsHandler(
    botInfo: ExtendedBot,
    private val locationService: LocationService,
    private val requestsExecutor: RequestsExecutor
) : CommandHandler(
    botInfo,
    command = arrayOf("get_locations"),
    commandDescription = "получение списка локаций"
) {
    override suspend fun handleCommand(message: CommonMessageImpl<*>, args: String?) {
        val locations = locationService.getAllLocations()

        requestsExecutor.sendMessage(
            message.chat,
            locations.joinToString(prefix = "Список локаций:\n", separator = "\n") { it.name }
        )
    }
}
