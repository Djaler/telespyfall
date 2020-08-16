package com.github.djaler.telespyfall.handlers.buttons

import com.github.djaler.telespyfall.handlers.CallbackQueryHandler
import com.github.djaler.telespyfall.service.GameService
import com.github.djaler.telespyfall.utils.userId
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.extensions.api.answers.answerCallbackQuery
import com.github.insanusmokrassar.TelegramBotAPI.types.CallbackQuery.MessageDataCallbackQuery
import org.springframework.stereotype.Component

@Component
class ReadGameInfoCallbackHandler(
    private val gameService: GameService,
    private val requestsExecutor: RequestsExecutor
) : CallbackQueryHandler() {
    override suspend fun handleCallback(query: MessageDataCallbackQuery, data: String) {
        val game = gameService.getGame(query.message.messageId)

        val playerInfo = game.players.find { it.telegramId == query.user.id.userId }
        if (playerInfo == null) {
            requestsExecutor.answerCallbackQuery(query, "Ты не играешь")
            return
        }

        requestsExecutor.answerCallbackQuery(
            query,
            if (playerInfo.spy) "Ты шпион" else "Локация - ${game.location.name}"
        )
    }
}
