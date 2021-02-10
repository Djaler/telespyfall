package com.github.djaler.telespyfall.components

import com.github.djaler.telespyfall.entity.GameEntity
import com.github.djaler.telespyfall.entity.GameState
import com.github.djaler.telespyfall.handlers.buttons.*
import com.github.djaler.telespyfall.utils.createCallbackDataForHandler
import com.github.insanusmokrassar.TelegramBotAPI.extensions.utils.types.buttons.InlineKeyboardMarkup
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.InlineKeyboardButtons.InlineKeyboardButton
import com.github.insanusmokrassar.TelegramBotAPI.types.buttons.InlineKeyboardMarkup
import org.springframework.stereotype.Component

@Component
class GameBoardFactory {
    private val joinGameButton = CallbackDataInlineKeyboardButton(
        "Вступить",
        createCallbackDataForHandler(JoinGameCallbackHandler::class.java)
    )
    private val leaveGameButton = CallbackDataInlineKeyboardButton(
        "Выйти",
        createCallbackDataForHandler(LeaveGameCallbackHandler::class.java)
    )
    private val startGameButton = CallbackDataInlineKeyboardButton(
        "Начать",
        createCallbackDataForHandler(StartGameCallbackHandler::class.java)
    )
    private val readGameInfoButton = CallbackDataInlineKeyboardButton(
        "Прочитать информацию",
        createCallbackDataForHandler(ReadGameInfoCallbackHandler::class.java)
    )
    private val finishGameButton = CallbackDataInlineKeyboardButton(
        "Завершить игру",
        createCallbackDataForHandler(FinishGameCallbackHandler::class.java)
    )
    private val recreateGameButton = CallbackDataInlineKeyboardButton(
        "Пересоздать игру",
        createCallbackDataForHandler(RecreateGameCallbackHandler::class.java)
    )

    data class GameBoard(val keyboard: InlineKeyboardMarkup, val text: String)

    @OptIn(ExperimentalStdlibApi::class)
    fun createGameBoard(game: GameEntity): GameBoard {
        val buttons = buildList<InlineKeyboardButton> {
            when (game.state) {
                GameState.CREATED -> {
                    add(joinGameButton)

                    if (game.players.isNotEmpty()) {
                        add(leaveGameButton)
                    }
                    if (game.players.size > 2) {
                        add(startGameButton)
                    }
                }
                GameState.STARTED -> {
                    add(readGameInfoButton)
                    add(finishGameButton)
                }
                GameState.FINISHED -> {
                    add(recreateGameButton)
                }
            }
        }

        val text = when (game.state) {
            GameState.CREATED -> {
                listOfNotNull(
                    "Набираем игроков",
                    if (game.players.isNotEmpty()) "Готовы играть: ${game.players.joinToString { it.username }}" else null,
                ).joinToString(". ")
            }
            GameState.STARTED -> "Играют: ${game.players.joinToString { it.username }}"
            GameState.FINISHED -> "Игру завершили: ${game.players.joinToString { it.username }}"
        }

        return GameBoard(InlineKeyboardMarkup(*buttons.toTypedArray()), text)
    }
}
