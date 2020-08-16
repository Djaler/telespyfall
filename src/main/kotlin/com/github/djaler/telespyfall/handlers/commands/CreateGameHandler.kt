package com.github.djaler.telespyfall.handlers.commands

import com.github.djaler.telespyfall.components.GameBoardFactory
import com.github.djaler.telespyfall.handlers.CommandHandler
import com.github.djaler.telespyfall.service.GameService
import com.github.insanusmokrassar.TelegramBotAPI.bot.RequestsExecutor
import com.github.insanusmokrassar.TelegramBotAPI.extensions.api.edit.text.editMessageText
import com.github.insanusmokrassar.TelegramBotAPI.extensions.api.send.sendMessage
import com.github.insanusmokrassar.TelegramBotAPI.types.ExtendedBot
import com.github.insanusmokrassar.TelegramBotAPI.types.message.CommonMessageImpl
import org.springframework.stereotype.Component

@Component
class CreateGameHandler(
    botInfo: ExtendedBot,
    private val gameService: GameService,
    private val requestsExecutor: RequestsExecutor,
    private val gameBoardFactory: GameBoardFactory
) : CommandHandler(
    botInfo,
    command = arrayOf("create_game"),
    commandDescription = "создание новой игры"
) {
    override suspend fun handleCommand(message: CommonMessageImpl<*>, args: String?) {
        val gameMessage = requestsExecutor.sendMessage(message.chat, "Игра создаётся")

        val game = gameService.createGame(gameMessage)

        val gameBoard = gameBoardFactory.createGameBoard(game)

        requestsExecutor.editMessageText(
            gameMessage,
            text = gameBoard.text,
            replyMarkup = gameBoard.keyboard
        )
    }
}
