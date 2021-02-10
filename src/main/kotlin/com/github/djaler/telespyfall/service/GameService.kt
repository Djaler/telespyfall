package com.github.djaler.telespyfall.service

import com.github.djaler.telespyfall.entity.GameEntity
import com.github.djaler.telespyfall.entity.GamePlayerEntity
import com.github.djaler.telespyfall.entity.GameState
import com.github.djaler.telespyfall.repository.GamePlayerRepository
import com.github.djaler.telespyfall.repository.GameRepository
import com.github.djaler.telespyfall.utils.userId
import com.github.djaler.telespyfall.utils.usernameOrName
import com.github.insanusmokrassar.TelegramBotAPI.types.MessageIdentifier
import com.github.insanusmokrassar.TelegramBotAPI.types.message.abstracts.Message
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.github.insanusmokrassar.TelegramBotAPI.types.User as TelegramUser

@Service
class GameService(
    private val gameRepository: GameRepository,
    private val gamePlayerRepository: GamePlayerRepository,
    private val locationService: LocationService
) {
    @Transactional
    fun createGame(message: Message): GameEntity {
        return gameRepository.save(
            GameEntity(
                messageId = message.messageId,
                location = locationService.getRandomLocation(),
                state = GameState.CREATED
            )
        )
    }

    fun getGame(messageId: MessageIdentifier): GameEntity {
        return gameRepository.findByMessageId(messageId)
            ?: throw IllegalArgumentException("No game with message id=$messageId")
    }

    fun addPlayer(game: GameEntity, telegramUser: TelegramUser): GameEntity {
        if (game.state !== GameState.CREATED) {
            throw IllegalStateException("Game ${game.id} already ${game.state}, can't add player")
        }

        if (game.players.find { it.telegramId == telegramUser.id.userId } !== null) {
            return game
        }

        gamePlayerRepository.save(
            GamePlayerEntity(
                game.id,
                telegramUser.id.userId,
                telegramUser.usernameOrName
            )
        )

        return gameRepository.findByIdOrNull(game.id)!!
    }

    fun removePlayer(game: GameEntity, telegramUser: TelegramUser): GameEntity {
        if (game.state !== GameState.CREATED) {
            throw IllegalStateException("Game ${game.id} already ${game.state}, can't remove player")
        }

        val player = game.players.find { it.telegramId == telegramUser.id.userId } ?: return game

        gamePlayerRepository.delete(player)

        return gameRepository.findByIdOrNull(game.id)!!
    }

    @Transactional
    fun startGame(game: GameEntity): GameEntity {
        if (game.state !== GameState.CREATED) {
            throw IllegalStateException("Game ${game.id} already ${game.state}, can't start")
        }

        val players = game.players

        if (players.size <= 2) {
            throw IllegalStateException("${players.size} players not enough to start")
        }

        val spyIndex = players.indices.random()

        gameRepository.save(game.copy(state = GameState.STARTED))
        gamePlayerRepository.saveAll(
            players.mapIndexed { index, player ->
                player.copy(spy = index == spyIndex)
            }
        )

        return gameRepository.findByIdOrNull(game.id)!!
    }

    @Transactional
    fun finishGame(game: GameEntity): GameEntity {
        if (game.state !== GameState.STARTED) {
            throw IllegalStateException("Game ${game.id} already ${game.state}, can't finish")
        }

        return gameRepository.save(
            game.copy(
                state = GameState.FINISHED
            )
        )
    }

    @Transactional
    fun recreateGame(game: GameEntity): GameEntity {
        if (game.state !== GameState.FINISHED) {
            throw IllegalStateException("Game ${game.id} already ${game.state}, can't recreate")
        }

        return gameRepository.save(
            game.copy(
                state = GameState.CREATED,
                location = locationService.getRandomLocation()
            )
        )
    }
}
