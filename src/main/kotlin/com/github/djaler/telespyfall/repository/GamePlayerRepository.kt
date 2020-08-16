package com.github.djaler.telespyfall.repository

import com.github.djaler.telespyfall.entity.GamePlayerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GamePlayerRepository : JpaRepository<GamePlayerEntity, Int>
