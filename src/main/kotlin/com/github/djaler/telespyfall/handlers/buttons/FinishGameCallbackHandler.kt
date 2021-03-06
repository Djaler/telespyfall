package com.github.djaler.telespyfall.handlers.buttons

import com.github.djaler.telespyfall.components.GameBoardFactory
import com.github.djaler.telespyfall.handlers.CallbackQueryHandler
import com.github.djaler.telespyfall.service.GameService
import com.github.djaler.telespyfall.utils.userId
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.extensions.api.answers.answerCallbackQuery
import com.github.insanusmokrassar.TelegramBotAPI.extensions.api.edit.text.editMessageText
import com.github.insanusmokrassar.TelegramBotAPI.types.CallbackQuery.MessageDataCallbackQuery
import org.springframework.stereotype.Component

@Component
class FinishGameCallbackHandler(
    private val gameService: GameService,
    private val requestsExecutor: RequestsExecutor,
    private val gameBoardFactory: GameBoardFactory
) : CallbackQueryHandler() {
    override suspend fun handleCallback(query: MessageDataCallbackQuery, data: String) {
        val originalGame = gameService.getGame(query.message.messageId)

        if (originalGame.players.find { it.telegramId == query.user.id.userId } == null) {
            requestsExecutor.answerCallbackQuery(query, "Ты не играешь")
            return
        }

        val game = gameService.finishGame(originalGame)

        val gameBoard = gameBoardFactory.createGameBoard(game)

        requestsExecutor.editMessageText(
            query.message.chat,
            query.message.messageId,
            text = gameBoard.text,
            replyMarkup = gameBoard.keyboard
        )
        requestsExecutor.answerCallbackQuery(query)
    }
}
