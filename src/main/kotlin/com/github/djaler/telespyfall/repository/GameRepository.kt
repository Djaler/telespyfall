package com.github.djaler.telespyfall.repository

import com.github.djaler.telespyfall.entity.GameEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository : JpaRepository<GameEntity, Int> {
    fun findByMessageId(messageId: Long): GameEntity?
}
